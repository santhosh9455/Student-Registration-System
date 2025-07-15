package com.example.srs.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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


    public UserProfileDTO(Long id, String name, String email, String phoneNumber, String gender, Integer age, String s, String s1, String s2, List<String> subjectNames, String s3, LocalDate localDate) {
    }
}
