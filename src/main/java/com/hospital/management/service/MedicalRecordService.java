package com.hospital.management.service;

import com.hospital.management.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicalRecordService {
    
    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);
    
    MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecord);
    
    void deleteMedicalRecord(Long id);
    
    MedicalRecord getMedicalRecordById(Long id);
    
    List<MedicalRecord> getMedicalRecordsByPatient(Long patientId);
    
    Page<MedicalRecord> getMedicalRecordsByPatient(Long patientId, Pageable pageable);
    
    List<MedicalRecord> getMedicalRecordsByDoctor(Long doctorId);
    
    MedicalRecord getMedicalRecordByAppointment(Long appointmentId);
}