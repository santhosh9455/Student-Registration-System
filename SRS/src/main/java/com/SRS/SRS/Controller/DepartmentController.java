package com.SRS.SRS.Controller;


import com.SRS.SRS.DTO.DepartmentDto;
import com.SRS.SRS.Models.DepartmentEntity;
import com.SRS.SRS.ServiceImplementation.DepartmentSerivecImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentSerivecImp deptservice;

    @GetMapping
    public List<DepartmentEntity> get(){
        return deptservice.getAll();
    }

    @PostMapping("/add-dept")
    public DepartmentDto GetDept(@RequestBody DepartmentDto departmentDto){
        DepartmentDto save =deptservice.saveDepartment(departmentDto);
        return save;
    }

    @PutMapping("/update-dept/{id}")
    public ResponseEntity<DepartmentEntity> updateStudent(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        DepartmentEntity update= deptservice.updateDeptPartial(id, departmentDto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDept(@PathVariable Long id ){
        deptservice.deleteDept(id);
        return "Deleted";
    }

}
