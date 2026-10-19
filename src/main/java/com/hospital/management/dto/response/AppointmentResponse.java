package com.hospital.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    
    private Long id;
    private LocalDateTime appointmentDate;
    private String status;
    private String notes;
    private PatientResponse patient;
    private DoctorResponse doctor;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}