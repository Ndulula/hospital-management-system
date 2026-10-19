package com.hospital.management.repository;

import com.hospital.management.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    
    /**
     * Find medical records by patient ID
     */
    List<MedicalRecord> findByPatientId(Long patientId);
    
    /**
     * Find medical records by patient ID with pagination
     */
    Page<MedicalRecord> findByPatientId(Long patientId, Pageable pageable);
    
    /**
     * Find medical records by doctor ID
     */
    List<MedicalRecord> findByDoctorId(Long doctorId);
    
    /**
     * Find medical record by appointment ID
     */
    MedicalRecord findByAppointmentId(Long appointmentId);  // ← Fix this line
}