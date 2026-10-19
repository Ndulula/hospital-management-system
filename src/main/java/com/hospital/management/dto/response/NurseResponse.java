package com.hospital.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NurseResponse {
    
    private Long id;
    private String nurseId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private DepartmentResponse department;
    private String shift;
    private String qualifications;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}