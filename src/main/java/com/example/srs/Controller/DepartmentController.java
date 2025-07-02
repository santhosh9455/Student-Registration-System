package com.example.srs.Controller;

import com.example.srs.DTO.DepartmentResDto;
import com.example.srs.ServiceImplementation.DepartmentServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    @Autowired
    private DepartmentServiceImp serviceImp;

    @PostMapping("/createDept")
    public ResponseEntity<DepartmentResDto> createDept(@Valid @RequestParam String departmentName) {
        return ResponseEntity.ok(serviceImp.createDept(departmentName));
    }

    @GetMapping("/AllDept")
    public ResponseEntity<List<DepartmentResDto>> getAllDept() {
        return ResponseEntity.ok(serviceImp.getAll());
    }

    @GetMapping("/getDept/{id}")
    public ResponseEntity<DepartmentResDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceImp.getdeptById(id));
    }

    @PatchMapping("/updateDept/{id}")
    public ResponseEntity<DepartmentResDto> updeteDept(@PathVariable Long id, @RequestParam String resDto) {
        return ResponseEntity.ok(serviceImp.updateDept(id, resDto));
    }

    @DeleteMapping("/deleteDept/{id}")
    public ResponseEntity<String> deleteDept(@PathVariable Long id) {
        return ResponseEntity.ok(serviceImp.delete(id));
    }
}
