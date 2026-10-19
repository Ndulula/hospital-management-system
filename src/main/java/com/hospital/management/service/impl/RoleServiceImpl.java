package com.hospital.management.service.impl;

import com.hospital.management.entity.Role;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.RoleRepository;
import com.hospital.management.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;
    
    @Override
    public Role createRole(Role role) {
        if (existsByName(role.getName())) {
            throw new IllegalArgumentException("Role with name " + role.getName() + " already exists");
        }
        return roleRepository.save(role);
    }
    
    @Override
    public Role updateRole(Long id, Role role) {
        Role existing = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        existing.setName(role.getName());
        existing.setDescription(role.getDescription());
        return roleRepository.save(existing);
    }
    
    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }
    
    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
    
    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}