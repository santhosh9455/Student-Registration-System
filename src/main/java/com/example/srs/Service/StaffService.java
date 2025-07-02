package com.example.srs.Service;

import com.example.srs.DTO.GetCourseRequestDto;
import com.example.srs.DTO.StaffResDto;
import com.example.srs.DTO.StudentResDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface StaffService {

    List<GetCourseRequestDto> getCourseRequest();

    StaffResDto getStaff();

    List<GetCourseRequestDto> getRejectedStudent();

    List<GetCourseRequestDto> getApprovedStudent();

    List<GetCourseRequestDto> getAlltudent();

    ResponseEntity<Map<String, String>> approveCoreStudent(Long studentId);

    ResponseEntity<Map<String, String>> rejectStudent(Long studentId);

    List<StudentResDto> getSubjectStudents();
}
