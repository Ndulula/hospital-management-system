package com.hospital.management.service;

import com.hospital.management.entity.Prescription;

import java.util.List;

public interface PrescriptionService {
    
    Prescription createPrescription(Prescription prescription);
    
    Prescription updatePrescription(Long id, Prescription prescription);
    
    void deletePrescription(Long id);
    
    Prescription getPrescriptionById(Long id);
    
    List<Prescription> getPrescriptionsByPatient(Long patientId);
    
    List<Prescription> getPrescriptionsByDoctor(Long doctorId);
    
    List<Prescription> getPrescriptionsByMedicalRecord(Long medicalRecordId);
    
    List<Prescription> searchPrescriptionsByMedicine(String medicineName);
}