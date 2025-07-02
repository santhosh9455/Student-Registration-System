package com.example.srs.DTO;

import com.example.srs.Enum.CourseRequestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentResDto {

    private Long id;

    private String name;

    private Integer age;

    private String gender;

    private String email;

    private String phoneNumber;

    private String departmentName;

    private String courseStatus;

    private String status;

    private String courseName;

    private String profileImagePath;

    private String marksheetImagePath;

    private String subjectName;

    private String username;


}
