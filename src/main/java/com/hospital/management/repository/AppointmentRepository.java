package com.hospital.management.repository;

import com.hospital.management.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByPatientId(Long patientId);
    
    List<Appointment> findByDoctorId(Long doctorId);
    
    List<Appointment> findByStatus(String status);
    
    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);
    
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
    
    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);
    
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :dateTime AND a.status != 'CANCELLED'")
    boolean existsByDoctorIdAndAppointmentDateAndStatusNotCancelled(@Param("doctorId") Long doctorId, @Param("dateTime") LocalDateTime dateTime);
    
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.status = :status")
    long countByStatus(@Param("status") String status);
    
    // Use this approach with parameters instead of CURRENT_DATE
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate >= :startOfDay AND a.appointmentDate < :endOfDay")
    List<Appointment> findTodayAppointments(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}