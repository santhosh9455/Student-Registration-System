package com.example.srs.ServiceImplementation;

import com.example.srs.DTO.GetCourseRequestDto;
import com.example.srs.DTO.StaffResDto;
import com.example.srs.DTO.StudentResDto;
import com.example.srs.Enum.CourseRequestStatusEnum;
import com.example.srs.Enum.StatusEnum;
import com.example.srs.Model.*;
import com.example.srs.Repository.CourseRepo;
import com.example.srs.Repository.StudentRepo;
import com.example.srs.Repository.UserProfileRepo;
import com.example.srs.Repository.UsersRepo;
import com.example.srs.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StaffServiceImp implements StaffService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Override
    public List<GetCourseRequestDto> getCourseRequest() {

        // 1. Get current logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. Get UsersEntity and check role
        UsersEntity user = usersRepo.findByUsername(username);

        // 3. Get UserProfileEntity (staff's profile)
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Staff profile found.");
        }else if ( profile.getCourse() == null){
           throw  new RuntimeException("course not assigned to you");
        }

        // 4. Get the assigned course
        CourseEntity course = profile.getCourse();

        // 5. Get students enrolled in this course with status PENDING
        List<StudentEntity> pendingStudents = studentRepo.findByCourse(course);

        if (pendingStudents == null)throw new RuntimeException("No student found");
        // 6. Map students to course request DTOs
        return pendingStudents.stream()
                .map(this::mapToGetCourseRequestDto) // You already have this mapper
                .collect(Collectors.toList());
    }

    private GetCourseRequestDto mapToGetCourseRequestDto(StudentEntity student) {
        GetCourseRequestDto dto = new GetCourseRequestDto();
        dto.setId(student.getId());
        dto.setStudentName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setCourseName(student.getCourse().getCourseName());
        dto.setDepartmentName(student.getDepartment().getDepartmentName());
        dto.setStatus(student.getCourseStatus().toString());
        return dto;
    }

    @Override
    public StaffResDto getStaff() {
        // 1. Get the logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. Fetch UsersEntity and check role
        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null || user.getRole() == null || !user.getRole().getName().equalsIgnoreCase("ROLE_STAFF")) {
            throw new RuntimeException("User is not assigned as STAFF.");
        }

        // 3. Fetch the associated profile
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Staff profile not found.");
        }

        // 4. Prepare response DTO
        StaffResDto resDto = new StaffResDto();
        resDto.setId(profile.getId());
        resDto.setName(profile.getName());
        resDto.setAge(profile.getAge());
        resDto.setGender(profile.getGender());
        resDto.setEmail(profile.getEmail());
        resDto.setPhoneNumber(profile.getPhoneNumber());

        if (profile.getSubjects() != null) {
            List<String> subjectNames = profile.getSubjects().stream().map(s->s.getSubjectName()).collect(Collectors.toList());
            resDto.setSubjectNme(subjectNames);
        }

        if (profile.getDepartment() != null) {
            resDto.setDepartmentName(profile.getDepartment().getDepartmentName());
        }

        if (profile.getCourse() != null) {
            resDto.setCourseName(profile.getCourse().getCourseName());
        } else {
            resDto.setCourseName("Not assigned");
        }

        return resDto;
    }

    @Override
    public List<GetCourseRequestDto> getRejectedStudent() {
        // 1. Get current logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. Get UsersEntity and validate role
        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found for username: " + username);
        }

        // 3. Get UserProfileEntity (staff details)
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Staff profile not found for user: " + username);
        }

        // 4. Check assigned course
        CourseEntity course = profile.getCourse();
        if (course == null) {
            throw new RuntimeException("No course assigned to this staff member.");
        }

        // 5. Get students in that course with status REJECTED
        List<StudentEntity> rejectedStudents = studentRepo.findByCourseAndCourseStatus(course, CourseRequestStatusEnum.REJECTED);

        // 6. Map to DTOs
        return rejectedStudents.stream()
                .map(this::mapToGetCourseRequestDto) // Assuming you have a working mapper
                .collect(Collectors.toList());
    }

    @Override
    public List<GetCourseRequestDto> getApprovedStudent() {
        // 1. Get current logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. Get UsersEntity and validate role
        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found for username: " + username);
        }

        if (user.getRole() == null || !user.getRole().getName().equalsIgnoreCase("ROLE_STAFF")) {
            throw new RuntimeException("Only STAFF users can access approved student requests.");
        }

        // 3. Get UserProfileEntity
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Staff profile not found for user: " + username);
        }

        // 4. Get assigned course
        CourseEntity course = profile.getCourse();
        if (course == null) {
            throw new RuntimeException("No course assigned to this staff member.");
        }

        // 5. Get students with APPROVED status for the course
        List<StudentEntity> approvedStudents = studentRepo.findByCourseAndCourseStatus(course, CourseRequestStatusEnum.APPROVED);

        // 6. Map to DTOs
        return approvedStudents.stream()
                .map(this::mapToGetCourseRequestDto) // Your existing mapping method
                .collect(Collectors.toList());
    }
    @Override
    public List<GetCourseRequestDto> getAlltudent() {
        // 1. Get current logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. Get UsersEntity and validate role
        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found for username: " + username);
        }


        // 3. Get staff profile
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Staff profile not found for user: " + username);
        }

        // 4. Get assigned course
        CourseEntity course = profile.getCourse();
        if (course == null) {
            throw new RuntimeException("No course assigned to this staff member.");
        }

        // 5. Fetch all students enrolled in this course
        List<StudentEntity> students = studentRepo.findByCourse(course);

        // 6. Map to DTOs
        return students.stream()
                .map(this::mapToGetCourseRequestDto) // Mapping method you already have
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Map<String, String>> approveCoreStudent(Long studentId) {

        Map<String, String> response = new HashMap<>();

        // 1. Get current logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. Get UsersEntity
        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null) {
            response.put("error", "User not found.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 3. Validate role
        if (user.getRole() == null || !user.getRole().getName().equalsIgnoreCase("ROLE_STAFF")) {
            response.put("error", "Only staff members can approve students.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // 4. Get UserProfileEntity and assigned course
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            response.put("error", "Staff profile not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CourseEntity staffCourse = profile.getCourse();
        if (staffCourse == null) {
            response.put("error", "No course assigned to the staff.");
            return ResponseEntity.badRequest().body(response);
        }

        // 5. Get the student entity
        Optional<StudentEntity> optionalStudent = studentRepo.findById(studentId);
        if (optionalStudent.isEmpty()) {
            response.put("error", "Student not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        StudentEntity student = optionalStudent.get();

        // 6. Check if student is enrolled in this staff's course
        if (student.getCourse() == null || !student.getCourse().getId().equals(staffCourse.getId())) {
            response.put("error", "You are not authorized to approve this student.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // 7. Update course status to APPROVED
        student.setCourseStatus(CourseRequestStatusEnum.APPROVED);
        StudentEntity saved = studentRepo.save(student);

        // 8. Send confirmation email
        emailService.sendSimpleEmail(
                saved.getEmail(),
                "Your Registration Request Approved",
                "Your Course registration request was successfully approved by staff.\n" +
                        "HOD will review your registration. If approved, you will be notified via email.\n\n" +
                        "Best Regards,\nStaff"
        );

        // 9. Return success response
        response.put("message", "Student approved successfully.");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> rejectStudent(Long studentId) {
        // 1. Get current logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. Get UsersEntity and validate
        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        if (user.getRole() == null || !user.getRole().getName().equalsIgnoreCase("ROLE_STAFF")) {
            throw new RuntimeException("Only staff members can reject students.");
        }

        // 3. Get staff profile and course
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Staff profile not found.");
        }

        CourseEntity staffCourse = profile.getCourse();
        if (staffCourse == null) {
            throw new RuntimeException("No course assigned to this staff.");
        }

        // 4. Get the student entity
        Optional<StudentEntity> optionalStudent = studentRepo.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found.");
        }

        StudentEntity student = optionalStudent.get();

        // 5. Check if student is enrolled in this staff's course
        if (student.getCourse() == null || !student.getCourse().getId().equals(staffCourse.getId())) {
            throw new RuntimeException("You are not authorized to reject this student.");
        }

        // 6. Update course status to REJECTED
        student.setCourseStatus(CourseRequestStatusEnum.REJECTED);
        StudentEntity saved = studentRepo.save(student);

        String email = saved.getEmail();
        String name = saved.getName();
        emailService.sendSimpleEmail(
                email,
                "Your Registration Request Rejected ",
                "Your Course registration request was Rejected by staff"+
                        "\n\n\n\n\n Best Regards,\n staff,"
        );

        Map<String, String> response = new HashMap<>();
        response.put("massage","Student rejected successfully.");
        return ResponseEntity.ok(response);
    }

    @Override
    public List<StudentResDto> getSubjectStudents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null || user.getProfile() == null) {
            throw new RuntimeException("Staff not found or not logged in.");
        }

        UserProfileEntity staffProfile = user.getProfile();
        Set<SubjectEntity> subject = staffProfile.getSubjects();

        if (subject == null) {
            throw new RuntimeException("No subject assigned to this staff.");
        }

        //  Find students directly mapped with this subject
        List<StudentEntity> students = studentRepo.findBySubjects(subject);

        return students.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<GetCourseRequestDto> getCourseFilerStud(String name, String courseStatus, int page, int size) {
        // 1. Get current authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null || user.getProfile() == null) {
            throw new RuntimeException("Staff profile not found");
        }

        // 2. Get course
        CourseEntity course = user.getProfile().getCourse();
        if (course == null) {
            throw new RuntimeException("Course not assigned to staff");
        }

        // 3. Handle filters
        String nameFilter = (name == null || name.isBlank()) ? null : name;


        CourseRequestStatusEnum statusEnum = null;
        if (courseStatus != null && !courseStatus.isBlank()) {
            try {
                statusEnum = CourseRequestStatusEnum.valueOf(courseStatus.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid course status: " + courseStatus);
            }
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentEntity> students = studentRepo.findByCourseAndFilters(course.getId(), nameFilter,  statusEnum, pageable);

        return students.map(this::mapToGetCourseRequestDto);
    }

    @Override
    public Page<StudentResDto> getFilterSubjectStudents(String name, int page, int size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UsersEntity user = usersRepo.findByUsername(username);
        if (user == null || user.getProfile() == null) {
            throw new RuntimeException("Staff not found or not logged in.");
        }

        Set<SubjectEntity> subjects = user.getProfile().getSubjects();
        if (subjects == null) {
            throw new RuntimeException("No subject assigned to this staff.");
        }

        if (name != null && name.trim().isEmpty()) {
            name = null;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentEntity> studentPage = studentRepo.findBySubjectsWithNameFilter(subjects, name, pageable);

        return studentPage.map(this::mapToDto);
    }


    private StudentResDto mapToDto(StudentEntity entity) {
    StudentResDto dto = new StudentResDto();

    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setAge(entity.getAge());
    dto.setGender(entity.getGender());
    dto.setEmail(entity.getEmail());
    dto.setPhoneNumber(entity.getPhoneNumber());

    // Handle department
    if (entity.getDepartment() != null) {
        dto.setDepartmentName(entity.getDepartment().getDepartmentName());
    } else {
        dto.setDepartmentName("Not Assigned");
    }

    // Handle subject
        if (entity.getSubjects() != null && !entity.getSubjects().isEmpty()) {
            List<String> subjectNames = entity.getSubjects().stream()
                    .map(SubjectEntity::getSubjectName) // Extract name from each SubjectEntity
                    .toList();
            dto.setSubjectName(subjectNames);
        } else {
            dto.setSubjectName(Collections.singletonList("Not Assigned"));
        }

    // Handle course
    if (entity.getCourse() != null) {
        dto.setCourseName(entity.getCourse().getCourseName());
    } else {
        dto.setCourseName("Not Assigned");
    }

    // Status
    dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : "PENDING");
    dto.setCourseStatus(entity.getCourseStatus() != null ? entity.getCourseStatus().name() : "NOT_REQUESTED");

    return dto;
}


//private StudentResDto mapToDto(StudentEntity student) {
//        StudentResDto dto = new StudentResDto();
//        dto.setId(student.getId());
//        dto.setName(student.getName());
//        dto.setEmail(student.getEmail());
//        dto.setAge(student.getAge());
//        dto.setGender(student.getGender());
//        dto.setPhoneNumber(student.getPhoneNumber());
//        dto.setDepartmentName(student.getDepartment().getDepartmentName());
//        return dto;
//    }

}
