package com.example.srs.Repository;

import com.example.srs.Model.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity, Long> {
    SubjectEntity findBySubjectName(String subjectNme);

    SubjectEntity findBySubjectNameAndDepartment_DepartmentName(String upperCase, String departmentName);

    List<SubjectEntity> findByDepartment_DepartmentName(String departmentName);
}
