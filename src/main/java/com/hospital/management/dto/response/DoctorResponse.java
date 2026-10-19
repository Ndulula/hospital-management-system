package com.hospital.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    
    private Long id;
    private String doctorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String specialization;
    private DepartmentResponse department;
    private String availability;
    private Double consultationFee;
    private String qualifications;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}