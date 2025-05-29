package com.SRS.SRS.ServiceImplementation;
import com.SRS.SRS.DTO.AdminDto;
import com.SRS.SRS.DTO.StudentDto;
import com.SRS.SRS.DTO.StudentUpdateDto;
import com.SRS.SRS.Models.AdminEntity;
import com.SRS.SRS.Models.StudentEntity;
import com.SRS.SRS.Repository.AdminRepo;
import com.SRS.SRS.Repository.DepartmentRepo;
import com.SRS.SRS.Repository.StudentRepo;
import com.SRS.SRS.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImplementation implements AdminService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AdminDto CreateAdmin(AdminDto adminDetail) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setId(adminDetail.getId()); // optional if ID is auto-generated
        adminEntity.setUsername(adminDetail.getUsername());
        adminEntity.setEmail(adminDetail.getEmail());
        adminEntity.setPassword(passwordEncoder.encode(adminDetail.getPassword()));

        // Save to repository
        AdminEntity savedEntity = adminRepo.save(adminEntity);

        // Inline: Entity → DTO
        AdminDto savedDto = new AdminDto();
        savedDto.setId(savedEntity.getId());
        savedDto.setUsername(savedEntity.getUsername());
        savedDto.setEmail(savedEntity.getEmail());
        savedDto.setPassword(savedEntity.getPassword());

        return savedDto;
    }



    private StudentDto mapToDTO(StudentEntity student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setUsername(student.getUsername());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        dto.setGender(student.getGender());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());

        dto.setDepartmentid(student.getDepartmentEntity().getId());

        return dto;
    }

    private StudentEntity mapToEntity(StudentDto dto) {
        StudentEntity student = new StudentEntity();

        if (dto.getId()!=null && dto.getId()>0) {
            student.setId(studentRepo.findById(dto.getId()).get().getId());
        }
        student.setUsername(dto.getUsername());
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGender(dto.getGender());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());


        student.setDepartmentEntity(departmentRepo.findById(dto.getDepartmentid()).get());

        return student;
    }




    @Override
    public StudentDto registerStudent(StudentDto studentDto,StudentUpdateDto studentUpdateDto) {

        if (studentRepo.findById(studentDto.getId()).isPresent()){
            updateStudentPartial(studentDto.getId(),studentUpdateDto);
        }

        System.out.println("log"+studentRepo.findByEmail(studentDto.getEmail()).isPresent());
        if (studentRepo.findByEmail(studentDto.getEmail()).isPresent()) {
            throw new RuntimeException(studentDto.getEmail()+" Email already exists");
        }

        StudentEntity student = mapToEntity(studentDto);
        StudentEntity saved = studentRepo.save(student);
        return mapToDTO(saved);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        StudentEntity student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return mapToDTO(student);
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        StudentEntity student = studentRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Not found"));
        return mapToDTO(student);
    }

    @Override
    public StudentDto getStudentByUsername(String username) {
        StudentEntity student = studentRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Not found"));
        return mapToDTO(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto dto) {
        StudentEntity student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        student.setUsername(dto.getUsername());
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGender(dto.getGender());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        return mapToDTO(studentRepo.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);
    }

    @Override
    public boolean checkStudentByEmail(String email) {
        return studentRepo.existsByEmail(email);
    }

    @Override
    public StudentEntity updateStudentPartial(Long id, StudentUpdateDto dto) {
        StudentEntity student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));

        // Only update non-null fields
        if (dto.getName() != null) {
            student.setName(dto.getName());
        }

        if (dto.getAge() != null) {
            student.setAge(dto.getAge());
        }

        if (dto.getGender() != null) {
            student.setGender(dto.getGender());
        }

        if (dto.getEmail() != null) {
            student.setEmail(dto.getEmail());
        }

        if (dto.getUsername() != null) {
            student.setUsername(dto.getUsername());
        }

        if (dto.getPhoneNumber() != null) {
            student.setPhoneNumber(dto.getPhoneNumber());
        }


        // Save the updated entity
        return studentRepo.save(student);
    }

//    @Override
//    public boolean userExist(StudentDto studentDto) {
//       boolean userexist= studentRepo.findByEmail(studentDto.getEmail());
//        return
//    }
}
