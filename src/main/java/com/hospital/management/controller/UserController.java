package com.hospital.management.controller;

import com.hospital.management.dto.request.ChangePasswordRequest;
import com.hospital.management.dto.request.UserRequest;
import com.hospital.management.dto.response.ApiResponse;
import com.hospital.management.dto.response.UserResponse;
import com.hospital.management.entity.User;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.service.UserService;
import com.hospital.management.util.EntityToDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;
    private final EntityToDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsernameOrEmail(username);
        UserResponse response = mapper.mapToUserResponse(user);
        return successResponse("Profile retrieved successfully", response);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UserRequest request) {
        String username = authentication.getName();
        User user = userService.findByUsernameOrEmail(username);
        
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        
        User updated = userService.updateUser(user.getId(), user);
        UserResponse response = mapper.mapToUserResponse(updated);
        return successResponse("Profile updated successfully", response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {
        String username = authentication.getName();
        User user = userService.findByUsernameOrEmail(username);
        
        // Verify old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return errorResponse("Current password is incorrect");
        }
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.updateUser(user.getId(), user);
        return successResponse("Password changed successfully", null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserResponse response = mapper.mapToUserResponse(user);
        return successResponse("User retrieved successfully", response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> responses = users.stream()
                .map(mapper::mapToUserResponse)
                .collect(Collectors.toList());
        return successResponse("All users retrieved", responses);
    }
}