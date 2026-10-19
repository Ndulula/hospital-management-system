package com.hospital.management.config;

import com.hospital.management.entity.Role;
import com.hospital.management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        // Create default roles if they don't exist
        String[] roles = {"ADMIN", "DOCTOR", "NURSE", "RECEPTIONIST", "USER"};
        for (String roleName : roles) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                role.setDescription(roleName + " role");
                roleRepository.save(role);
                System.out.println("Created role: " + roleName);
            }
        }
    }
}