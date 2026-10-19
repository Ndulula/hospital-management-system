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
@Table(name = "medical_records")
public class MedicalRecord extends BaseEntity {
    
    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;
    
    @Column(length = 1000)
    private String diagnosis;
    
    @Column(length = 1000)
    private String symptoms;
    
    @Column(length = 1000)
    private String treatment;
    
    @Column(name = "doctor_notes", length = 2000)
    private String doctorNotes;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    @OneToOne(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    private Prescription prescription;  // ← Add this field!
}