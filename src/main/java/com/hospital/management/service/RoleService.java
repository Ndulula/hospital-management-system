package com.hospital.management.service;

import com.hospital.management.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    
    Role createRole(Role role);
    
    Role updateRole(Long id, Role role);
    
    void deleteRole(Long id);
    
    Optional<Role> getRoleById(Long id);
    
    Optional<Role> getRoleByName(String name);
    
    List<Role> getAllRoles();
    
    boolean existsByName(String name);
}