package com.hospital.management.service;

import com.hospital.management.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    User createUser(User user);
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
    
    Optional<User> getUserById(Long id);
    
    Optional<User> getUserByUsername(String username);
    
    Optional<User> getUserByEmail(String email);
    
    List<User> getAllUsers();
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    User findByUsernameOrEmail(String usernameOrEmail);
}