package com.hospital.management.repository;

import com.hospital.management.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    
    /**
     * Find prescriptions by patient ID
     */
    List<Prescription> findByPatientId(Long patientId);
    
    /**
     * Find prescriptions by doctor ID
     */
    List<Prescription> findByDoctorId(Long doctorId);
    
    /**
     * Find prescriptions by medical record (using medicalRecord's id)
     */
    List<Prescription> findByMedicalRecordId(Long medicalRecordId);
    
    /**
     * Find prescriptions by medicine name (contains)
     */
    List<Prescription> findByMedicineNameContaining(String medicineName);
}