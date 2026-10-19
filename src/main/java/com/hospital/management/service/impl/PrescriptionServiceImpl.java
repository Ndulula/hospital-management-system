package com.hospital.management.service.impl;

import com.hospital.management.entity.Prescription;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.PrescriptionRepository;
import com.hospital.management.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {
    
    private final PrescriptionRepository prescriptionRepository;
    
    @Override
    public Prescription createPrescription(Prescription prescription) {
        prescription.setStartDate(LocalDate.now());
        return prescriptionRepository.save(prescription);
    }
    
    @Override
    public Prescription updatePrescription(Long id, Prescription prescription) {
        Prescription existing = prescriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
        existing.setMedicineName(prescription.getMedicineName());
        existing.setDosage(prescription.getDosage());
        existing.setFrequency(prescription.getFrequency());
        existing.setDuration(prescription.getDuration());
        existing.setDoctorNotes(prescription.getDoctorNotes());
        existing.setEndDate(prescription.getEndDate());
        return prescriptionRepository.save(existing);
    }
    
    @Override
    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prescription not found with id: " + id);
        }
        prescriptionRepository.deleteById(id);
    }
    
    @Override
    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
    }
    
    @Override
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }
    
    @Override
    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }
    
    @Override
    public List<Prescription> getPrescriptionsByMedicalRecord(Long medicalRecordId) {
        return prescriptionRepository.findByMedicalRecordId(medicalRecordId);
    }
    
    @Override
    public List<Prescription> searchPrescriptionsByMedicine(String medicineName) {
        return prescriptionRepository.findByMedicineNameContaining(medicineName);
    }
}