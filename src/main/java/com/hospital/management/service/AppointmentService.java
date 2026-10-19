package com.hospital.management.service;

import com.hospital.management.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    
    Appointment createAppointment(Appointment appointment);
    
    Appointment updateAppointment(Long id, Appointment appointment);
    
    void cancelAppointment(Long id);
    
    void completeAppointment(Long id);
    
    Appointment getAppointmentById(Long id);

    Page<Appointment> getAllAppointments(Pageable pageable);
    
    List<Appointment> getAppointmentsByPatient(Long patientId);
    
    List<Appointment> getAppointmentsByDoctor(Long doctorId);
    
    List<Appointment> getAppointmentsByStatus(String status);
    
    Page<Appointment> getAppointmentsByPatient(Long patientId, Pageable pageable);
    
    Page<Appointment> getAppointmentsByDoctor(Long doctorId, Pageable pageable);
    
    List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end);
    
    List<Appointment> getTodayAppointments();
    
    boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime);
    
    long countAppointmentsByStatus(String status);
}
