package com.SRS.SRS.Service;

import com.SRS.SRS.DTO.AdminDto;
import com.SRS.SRS.DTO.StudentDto;
import com.SRS.SRS.DTO.StudentUpdateDto;
import com.SRS.SRS.Models.StudentEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface AdminService {

    AdminDto CreateAdmin(AdminDto adminDetail);
    StudentDto registerStudent(StudentDto studentDto,StudentUpdateDto studentUpdateDto);
    StudentDto getStudentById(Long id);
    StudentDto getStudentByEmail(String email);
    StudentDto getStudentByUsername(String username);
    List<StudentDto> getAllStudents();
    StudentDto updateStudent(Long id, StudentDto studentDto);
    void deleteStudent(Long id);

    boolean checkStudentByEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email);

    StudentEntity updateStudentPartial(Long id, StudentUpdateDto studentUpdateDto);
//    boolean userExist();
}
