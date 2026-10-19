package com.hospital.management.service.impl;

import com.hospital.management.entity.Patient;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
    
    private final PatientRepository patientRepository;
    
    @Override
    public Patient createPatient(Patient patient) {
        if (existsByPatientId(patient.getPatientId())) {
            throw new IllegalArgumentException("Patient with ID " + patient.getPatientId() + " already exists");
        }
        return patientRepository.save(patient);
    }
    
    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        
        // Update fields
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());
        existingPatient.setGender(patient.getGender());
        existingPatient.setBloodGroup(patient.getBloodGroup());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setPhoneNumber(patient.getPhoneNumber());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setEmergencyContact(patient.getEmergencyContact());
        existingPatient.setEmergencyPhone(patient.getEmergencyPhone());
        existingPatient.setMedicalHistory(patient.getMedicalHistory());
        
        return patientRepository.save(existingPatient);
    }
    
    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
    
    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    
    @Override
    public Optional<Patient> getPatientByPatientId(String patientId) {
        return patientRepository.findByPatientId(patientId);
    }
    
    @Override
    public List<Patient> searchPatients(String searchTerm) {
        return patientRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
    }
    
    @Override
    public Page<Patient> searchPatients(String searchTerm, Pageable pageable) {
        return patientRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm, pageable);
    }
    
    @Override
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }
    
    @Override
    public List<Patient> getPatientsByGender(String gender) {
        return patientRepository.findByGender(gender);
    }
    
    @Override
    public Page<Patient> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }
    
    @Override
    public boolean existsByPatientId(String patientId) {
        return patientRepository.existsByPatientId(patientId);
    }
    
    @Override
    public long countPatientsByGender(String gender) {
        return patientRepository.countByGender(gender);
    }

    @Override
    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }
}