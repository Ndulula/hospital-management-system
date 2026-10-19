package com.hospital.management.service.impl;

import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.MedicalRecordRepository;
import com.hospital.management.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {
    
    private final MedicalRecordRepository medicalRecordRepository;
    
    @Override
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecord.setRecordDate(java.time.LocalDateTime.now());
        return medicalRecordRepository.save(medicalRecord);
    }
    
    @Override
    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecord) {
        MedicalRecord existing = medicalRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
        existing.setDiagnosis(medicalRecord.getDiagnosis());
        existing.setSymptoms(medicalRecord.getSymptoms());
        existing.setTreatment(medicalRecord.getTreatment());
        existing.setDoctorNotes(medicalRecord.getDoctorNotes());
        return medicalRecordRepository.save(existing);
    }
    
    @Override
    public void deleteMedicalRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medical record not found with id: " + id);
        }
        medicalRecordRepository.deleteById(id);
    }
    
    @Override
    public MedicalRecord getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
    }
    
    @Override
    public List<MedicalRecord> getMedicalRecordsByPatient(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }
    
    @Override
    public Page<MedicalRecord> getMedicalRecordsByPatient(Long patientId, Pageable pageable) {
        return medicalRecordRepository.findByPatientId(patientId, pageable);
    }
    
    @Override
    public List<MedicalRecord> getMedicalRecordsByDoctor(Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId);
    }
    
    @Override
    public MedicalRecord getMedicalRecordByAppointment(Long appointmentId) {
        return medicalRecordRepository.findByAppointmentId(appointmentId);
    }
}