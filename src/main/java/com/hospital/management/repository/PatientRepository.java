package com.hospital.management.repository;

import com.hospital.management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    /**
     * Find patient by patient ID
     */
    Optional<Patient> findByPatientId(String patientId);
    
    /**
     * Search patients by name (contains)
     */
    List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    /**
     * Search patients by name with pagination
     */
    Page<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
    
    /**
     * Find patients by blood group
     */
    List<Patient> findByBloodGroup(String bloodGroup);
    
    /**
     * Find patients by gender
     */
    List<Patient> findByGender(String gender);
    
    /**
     * Check if patient ID exists
     */
    boolean existsByPatientId(String patientId);
    
    /**
     * Count patients by gender
     */
    @Query("SELECT COUNT(p) FROM Patient p WHERE p.gender = :gender")
    long countByGender(@Param("gender") String gender);
}