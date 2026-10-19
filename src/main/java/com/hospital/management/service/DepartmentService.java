package com.hospital.management.service;

import com.hospital.management.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    
    Department createDepartment(Department department);
    
    Department updateDepartment(Long id, Department department);
    
    void deleteDepartment(Long id);
    
    Optional<Department> getDepartmentById(Long id);
    
    Optional<Department> getDepartmentByName(String name);
    
    List<Department> getAllDepartments();
    
    boolean existsByName(String name);
}