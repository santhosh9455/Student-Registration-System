package com.example.srs.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phoneNumber;
    private String gender;
    private Integer age;

    private LocalDate dateOfBirth;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToMany
    private Set<SubjectEntity> subjects = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private CourseEntity course;   // only used for STAFF
}

