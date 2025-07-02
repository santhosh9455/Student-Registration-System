package com.example.srs.Controller;

import com.example.srs.DTO.GetCourseRequestDto;
import com.example.srs.DTO.StaffResDto;
import com.example.srs.DTO.StudentResDto;
import com.example.srs.ServiceImplementation.StaffServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = "http://localhost:4200")
public class StaffController {

    @Autowired
    private StaffServiceImp serviceImp;

    @GetMapping("/getRequestedStudent")
    public ResponseEntity<List<GetCourseRequestDto>> getPendingStudent() {
        List<GetCourseRequestDto> core = serviceImp.getCourseRequest();
        return ResponseEntity.ok(core);
    }

    @GetMapping("/getRejectedStudent")
    public ResponseEntity<List<GetCourseRequestDto>> getRejectedStudent() {
        List<GetCourseRequestDto> core = serviceImp.getRejectedStudent();
        return ResponseEntity.ok(core);
    }

    @GetMapping("/getApprovedStudent")
    public ResponseEntity<List<GetCourseRequestDto>> getApprovedStudent() {
        List<GetCourseRequestDto> core = serviceImp.getApprovedStudent();
        return ResponseEntity.ok(core);
    }

    @GetMapping("/getAlltudent")
    public ResponseEntity<List<GetCourseRequestDto>> getAlltudent() {
        List<GetCourseRequestDto> core = serviceImp.getAlltudent();
        return ResponseEntity.ok(core);
    }

    @GetMapping("/getStaff")
    public ResponseEntity<StaffResDto> getStaff() {
        StaffResDto resDto = serviceImp.getStaff();
        return ResponseEntity.ok(resDto);
    }

    @PostMapping("/approveStudent/{studentId}")
    public ResponseEntity<Map<String, String>> approveStudent(@PathVariable Long studentId) {
        return serviceImp.approveCoreStudent(studentId);
    }

    @PostMapping("/RejectStudent/{studentId}")
    public ResponseEntity<Map<String, String>>rejectStudent(@PathVariable Long studentId) {
        return serviceImp.rejectStudent(studentId);
    }

    @GetMapping("/getSubjectStudents")
    public ResponseEntity<List<StudentResDto>> getSubjectStudents() {
        return ResponseEntity.ok(serviceImp.getSubjectStudents());
    }

}
