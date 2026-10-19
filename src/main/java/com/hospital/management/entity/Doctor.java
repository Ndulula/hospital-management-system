package com.hospital.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity {
    
    @Column(name = "doctor_id", nullable = false, unique = true)
    private String doctorId;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(nullable = false)
    private String specialization;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;  // ← Make sure this is the correct import
    
    @Column(name = "availability")
    private String availability;
    
    @Column(name = "consultation_fee")
    private Double consultationFee;
    
    @Column(length = 1000)
    private String qualifications;
}