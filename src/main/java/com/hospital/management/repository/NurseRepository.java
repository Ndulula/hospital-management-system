package com.hospital.management.repository;

import com.hospital.management.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    
    /**
     * Find nurse by nurse ID
     */
    Optional<Nurse> findByNurseId(String nurseId);
    
    /**
     * Find nurses by shift
     */
    List<Nurse> findByShift(String shift);
    
    /**
     * Find nurses by department ID
     */
    List<Nurse> findByDepartmentId(Long departmentId);
    
    /**
     * Search nurses by name
     */
    List<Nurse> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    /**
     * Check if nurse ID exists
     */
    boolean existsByNurseId(String nurseId);
    
    /**
     * Count nurses by shift
     */
    @Query("SELECT COUNT(n) FROM Nurse n WHERE n.shift = :shift")
    long countByShift(@Param("shift") String shift);
}