package com.SRS.SRS.ServiceImplementation;

import com.SRS.SRS.DTO.DepartmentDto;
import com.SRS.SRS.Models.DepartmentEntity;
import com.SRS.SRS.Repository.DepartmentRepo;
import com.SRS.SRS.Service.DepartmentSerivec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentSerivecImp implements DepartmentSerivec {

    @Autowired
    private DepartmentRepo departmentRepo;

    private DepartmentDto mapToDTO(DepartmentEntity deptEntit) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(deptEntit.getId());
        dto.setName(deptEntit.getName());
        return dto;
    }

    private DepartmentEntity mapToEntity(DepartmentDto dto) {
        DepartmentEntity dept = new DepartmentEntity();
        dept.setId(dto.getId());
        dept.setName(dto.getName());
        return dept;
    }

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        DepartmentEntity deptDto = mapToEntity(departmentDto);
        DepartmentEntity save = departmentRepo.save(deptDto);
        return mapToDTO(save);
    }

    @Override
    public List<DepartmentEntity> getAll() {
        return departmentRepo.findAll();
    }

    @Override
    public DepartmentEntity updateDeptPartial(Long id, DepartmentDto departmentDto) {
        DepartmentEntity dept = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("dept not found with ID: " + id));
        // Save the updated entity
        return departmentRepo.save(dept);
    }

    @Override
    public void deleteDept(Long id) {
        departmentRepo.deleteById(id);
    }
}
