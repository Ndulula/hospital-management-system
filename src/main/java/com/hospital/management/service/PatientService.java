package com.hospital.management.service;

import com.hospital.management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    
    Patient createPatient(Patient patient);
    
    Patient updatePatient(Long id, Patient patient);
    
    void deletePatient(Long id);
    
    Optional<Patient> getPatientById(Long id);
    
    Optional<Patient> getPatientByPatientId(String patientId);
    
    List<Patient> searchPatients(String searchTerm);
    
    Page<Patient> searchPatients(String searchTerm, Pageable pageable);
    
    List<Patient> getPatientsByBloodGroup(String bloodGroup);
    
    List<Patient> getPatientsByGender(String gender);
    
    Page<Patient> getAllPatients(Pageable pageable);
    
    boolean existsByPatientId(String patientId);
    
    long countPatientsByGender(String gender);

    boolean existsById(Long id);
}