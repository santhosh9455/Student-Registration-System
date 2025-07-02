package com.example.srs.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class UserProfileDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private Integer age;

    private LocalDate dateOfBirth;
    private String username; // from UsersEntity
    private String role;     // from UsersEntity -> RoleEntity
    private String departmentName; // from DepartmentEntity
    private String subjectName;    // from SubjectEntity
    private String courseName;     // from CourseEntity

    // Constructors
    public UserProfileDTO() {}

    public UserProfileDTO(Long id, String name, String email, String phoneNumber, String gender, Integer age,
                          String username, String role, String departmentName, String subjectName, String courseName, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
        this.username = username;
        this.role = role;
        this.departmentName = departmentName;
        this.subjectName = subjectName;
        this.courseName = courseName;
        this.dateOfBirth = dateOfBirth;
    }
}
