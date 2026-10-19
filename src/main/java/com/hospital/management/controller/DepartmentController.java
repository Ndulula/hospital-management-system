package com.hospital.management.controller;

import com.hospital.management.dto.response.ApiResponse;
import com.hospital.management.dto.response.DepartmentResponse;
import com.hospital.management.entity.Department;
import com.hospital.management.service.DepartmentService;
import com.hospital.management.util.EntityToDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController extends BaseController {
    
    private final DepartmentService departmentService;
    private final EntityToDtoMapper mapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @Valid @RequestBody Department department) {
        Department created = departmentService.createDepartment(department);
        DepartmentResponse response = mapper.mapToDepartmentResponse(created);
        return createdResponse("Department created successfully", response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartment(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        DepartmentResponse response = mapper.mapToDepartmentResponse(department);
        return successResponse("Department retrieved", response);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentResponse> responses = departments.stream()
                .map(mapper::mapToDepartmentResponse)
                .collect(Collectors.toList());
        return successResponse("All departments retrieved", responses);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentByName(
            @PathVariable String name) {
        Department department = departmentService.getDepartmentByName(name)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        DepartmentResponse response = mapper.mapToDepartmentResponse(department);
        return successResponse("Department retrieved", response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody Department department) {
        Department updated = departmentService.updateDepartment(id, department);
        DepartmentResponse response = mapper.mapToDepartmentResponse(updated);
        return successResponse("Department updated", response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return noContentResponse();
    }
}