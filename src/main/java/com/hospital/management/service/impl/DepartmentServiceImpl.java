package com.hospital.management.service.impl;

import com.hospital.management.entity.Department;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.DepartmentRepository;
import com.hospital.management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    @Override
    public Department createDepartment(Department department) {
        if (existsByName(department.getName())) {
            throw new IllegalArgumentException("Department with name " + department.getName() + " already exists");
        }
        return departmentRepository.save(department);
    }
    
    @Override
    public Department updateDepartment(Long id, Department department) {
        Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        existing.setName(department.getName());
        existing.setDescription(department.getDescription());
        existing.setHeadOfDepartment(department.getHeadOfDepartment());
        existing.setPhoneNumber(department.getPhoneNumber());
        existing.setLocation(department.getLocation());
        return departmentRepository.save(existing);
    }
    
    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
    
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
    
    @Override
    public Optional<Department> getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }
    
    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }
}