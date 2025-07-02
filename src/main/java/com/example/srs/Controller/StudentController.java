package com.example.srs.Controller;

import com.example.srs.DTO.CourseResDto;
import com.example.srs.DTO.DepartmentDetailsDto;
import com.example.srs.DTO.StudentRequestDto;
import com.example.srs.DTO.StudentResDto;
import com.example.srs.ServiceImplementation.StudentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentServiceImp serviceImp;

    @CrossOrigin(origins = "http://localhost:4200") // or your frontend URL
    @PostMapping(value = "/registerRequest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentResDto> registerStudent(@ModelAttribute StudentRequestDto form) {

        StudentRequestDto dto = new StudentRequestDto();
        dto.setName(form.getName());
        dto.setDepartmentId(form.getDepartmentId());
        dto.setEmail(form.getEmail());
        dto.setAge(form.getAge());
        dto.setGender(form.getGender());
        dto.setPhoneNumber(form.getPhoneNumber());

        StudentResDto response = serviceImp.RegisterRequest(dto, form.getProfileImage(), form.getMarksheetImage());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/getStudent")
    public ResponseEntity<StudentResDto> getStudentById() {
        StudentResDto student = serviceImp.getStudent();
        return ResponseEntity.ok(student);
    }

    @PostMapping("/registerCourse/{courseId}")
    public ResponseEntity<StudentResDto> registerCourse(@PathVariable Long courseId) {
        StudentResDto resDto = serviceImp.registerCourse(courseId);
        return ResponseEntity.ok(resDto);
    }

    @GetMapping("/departmentInfo")
    public ResponseEntity<DepartmentDetailsDto> getDetailsDept(){
        DepartmentDetailsDto detailsDto = serviceImp.getDetailsDept();
        return ResponseEntity.ok(detailsDto);
    }

//    @PostMapping(value = "/uploadExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> uploadExcelFile(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("departmentName") String departmentName) {
//
//        if (!file.getOriginalFilename().endsWith(".xlsx")) {
//            return ResponseEntity.badRequest().body("Only .xlsx Excel files are supported.");
//        }
//
//        serviceImp.saveStudentsFromExcel(file, departmentName);
//        return ResponseEntity.ok("Excel data uploaded and saved successfully.");
//    }

    @GetMapping("/courseList")
    public ResponseEntity<List<CourseResDto>> courseList(){
        List<CourseResDto> core = serviceImp.courseList();
        return ResponseEntity.ok(core);
    }
}
