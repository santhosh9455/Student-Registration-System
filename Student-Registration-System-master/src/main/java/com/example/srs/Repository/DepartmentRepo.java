package com.example.srs.Repository;

import com.example.srs.Model.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByDepartmentName(String departmentName);
}
