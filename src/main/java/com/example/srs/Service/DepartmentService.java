package com.example.srs.Service;

import com.example.srs.DTO.DepartmentDto;
import com.example.srs.DTO.DepartmentResDto;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentResDto createDept(String dto);

    List<DepartmentResDto> getAll();

    DepartmentResDto getdeptById(Long id);


    DepartmentResDto updateDept(Long id, String resDto);

    String delete(Long id);
}
