package com.SRS.SRS.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Student_Details")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private Integer age;
    private String gender;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @ManyToOne
    private DepartmentEntity departmentEntity;
}
