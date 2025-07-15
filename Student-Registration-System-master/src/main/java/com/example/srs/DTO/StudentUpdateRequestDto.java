package com.example.srs.DTO;

import com.example.srs.Enum.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentUpdateRequestDto {

    private String name;
    private Integer age;
    private String gender;
    private String email;
    private String phoneNumber;
    private Long departmentId;
    private Long courseId;
    private Long subjectId;
    private String status;
    private String username;
    private String courseStatus;
    private String profileImagePath;
    private String marksheetImagePath;
}
