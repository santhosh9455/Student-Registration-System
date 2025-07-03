package com.example.srs.Service;

import com.example.srs.DTO.*;
import com.example.srs.Enum.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface HodService {
    StudentResDto approveStudent(Long id);

    List<StudentResDto> getAllRequestedStud();

    StudentResDto rejectStudent(Long id);

    List<StudentResDto> approvedStudentlist();

    List<StudentResDto> rejectedStudentlist();

    StudentResDto getStudent(Long id);

    String deleteByid(Long id);

    StaffResDto createStaff(StaffDto dto);

    SubjectResDto createSubject(String subjectName);

    List<SubjectResDto> getAllSubject();

    SubjectResDto getSubjectByName(String subjectName);

    SubjectResDto updateSubjectName(Long id, String newSubjectName );

    StaffResDto updateStaff(Long id, StaffResDto dto);

    String deleteStaff(Long id);

    String deleteSubject(Long id);

    List<StaffResDto> getAllStaff();

    CourseResDto createCourse(String courseName);

    List<CourseResDto> getAllCourse();

    Map<String, String> deleteCourse(Long id);

    StudentResDto updateStudent(Long id, StudentUpdateRequestDto dto, MultipartFile profileImage, MultipartFile marksheetImage);

    Page<StudentResDto> getFilteredRequestedStudents(String name, StatusEnum status, int page, int size);

    Page<CourseResDto> filteredCourse(String search, int page, int size);

    CourseResDto updateCourse( CourseResDto dto);
}
