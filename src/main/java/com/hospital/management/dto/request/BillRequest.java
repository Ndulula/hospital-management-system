package com.hospital.management.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    
    @PositiveOrZero(message = "Consultation fee must be zero or positive")
    private Double consultationFee;
    
    @PositiveOrZero(message = "Laboratory charges must be zero or positive")
    private Double laboratoryCharges;
    
    @PositiveOrZero(message = "Medication charges must be zero or positive")
    private Double medicationCharges;
    
    @PositiveOrZero(message = "Other services must be zero or positive")
    private Double otherServices;
    
    @PositiveOrZero(message = "Discount must be zero or positive")
    private Double discountAmount;
    
    private String notes;
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    private Long appointmentId;
}