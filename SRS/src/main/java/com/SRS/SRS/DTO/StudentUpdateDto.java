package com.SRS.SRS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentUpdateDto {

    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String email;
    private String username;
    private String phoneNumber;
    private String password;
    private String department;

}

