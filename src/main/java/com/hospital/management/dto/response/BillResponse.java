package com.hospital.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    
    private Long id;
    private String billNumber;
    private Double consultationFee;
    private Double laboratoryCharges;
    private Double medicationCharges;
    private Double otherServices;
    private Double totalAmount;
    private Double taxAmount;
    private Double discountAmount;
    private Double finalAmount;
    private String status;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String notes;
    private PatientResponse patient;
    private AppointmentResponse appointment;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}