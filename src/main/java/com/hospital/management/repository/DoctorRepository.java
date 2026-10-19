package com.hospital.management.repository;

import com.hospital.management.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    /**
     * Find doctor by doctor ID
     */
    Optional<Doctor> findByDoctorId(String doctorId);
    
    /**
     * Find doctors by specialization
     */
    List<Doctor> findBySpecialization(String specialization);
    
    /**
     * Find doctors by department ID
     */
    List<Doctor> findByDepartmentId(Long departmentId);
    
    /**
     * Find doctors by specialization with pagination
     */
    Page<Doctor> findBySpecialization(String specialization, Pageable pageable);
    
    /**
     * Search doctors by name
     */
    List<Doctor> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    /**
     * Check if doctor ID exists
     */
    boolean existsByDoctorId(String doctorId);
    
    /**
     * Find doctors with availability
     */
    @Query("SELECT d FROM Doctor d WHERE d.availability IS NOT NULL")
    List<Doctor> findAvailableDoctors();
    
    /**
     * Count doctors by department
     */
    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.department.id = :departmentId")
    long countByDepartmentId(@Param("departmentId") Long departmentId);
}