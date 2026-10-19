package com.hospital.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bills")
public class Bill extends BaseEntity {
    
    @Column(name = "bill_number", nullable = false, unique = true)
    private String billNumber;
    
    @Column(name = "consultation_fee")
    private Double consultationFee;
    
    @Column(name = "laboratory_charges")
    private Double laboratoryCharges;
    
    @Column(name = "medication_charges")
    private Double medicationCharges;
    
    @Column(name = "other_services")
    private Double otherServices;
    
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    
    @Column(name = "tax_amount")
    private Double taxAmount;
    
    @Column(name = "discount_amount")
    private Double discountAmount;
    
    @Column(name = "final_amount", nullable = false)
    private Double finalAmount;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    
    @Column(length = 500)
    private String notes;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
