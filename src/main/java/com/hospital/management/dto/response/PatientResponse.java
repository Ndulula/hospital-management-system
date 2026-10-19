package com.hospital.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    
    private Long id;
    private String patientId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String address;
    private String phoneNumber;
    private String email;
    private String emergencyContact;
    private String emergencyPhone;
    private String medicalHistory;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}