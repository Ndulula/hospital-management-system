package com.hospital.management.repository;

import com.hospital.management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Find a role by its name
     */
    Optional<Role> findByName(String name);
    
    /**
     * Check if a role exists by name
     */
    boolean existsByName(String name);
}
