package com.SRS.SRS.Service;

import com.SRS.SRS.DTO.DepartmentDto;
import com.SRS.SRS.Models.DepartmentEntity;

import java.util.List;

public interface DepartmentSerivec {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    List<DepartmentEntity> getAll();

    DepartmentEntity updateDeptPartial(Long id, DepartmentDto departmentDto);

    void deleteDept(Long id);

}
