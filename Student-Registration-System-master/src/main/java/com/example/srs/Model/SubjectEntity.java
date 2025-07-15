package com.example.srs.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "subject_tbl")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<StudentEntity> students;

    @ManyToMany(mappedBy = "subjects")
    private List<UserProfileEntity> staffProfiles = new ArrayList<>();

}
