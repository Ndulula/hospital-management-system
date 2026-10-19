package com.hospital.management.service.impl;

import com.hospital.management.entity.Appointment;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    
    @Override
    public Appointment createAppointment(Appointment appointment) {
        // Validate patient exists
        patientRepository.findById(appointment.getPatient().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        
        // Validate doctor exists
        doctorRepository.findById(appointment.getDoctor().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        
        // Check if doctor is available
        if (!isDoctorAvailable(appointment.getDoctor().getId(), appointment.getAppointmentDate())) {
            throw new IllegalStateException("Doctor is not available at the requested time");
        }
        
        appointment.setStatus("PENDING");
        return appointmentRepository.save(appointment);
    }
    
    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        Appointment existing = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        
        existing.setAppointmentDate(appointment.getAppointmentDate());
        existing.setStatus(appointment.getStatus());
        existing.setNotes(appointment.getNotes());
        
        return appointmentRepository.save(existing);
    }
    
    @Override
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }
    
    @Override
    public void completeAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        appointment.setStatus("COMPLETED");
        appointmentRepository.save(appointment);
    }
    
    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    @Override
    public Page<Appointment> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }
    
    @Override
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
    
    @Override
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
    
    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
    
    @Override
    public Page<Appointment> getAppointmentsByPatient(Long patientId, Pageable pageable) {
        return appointmentRepository.findByPatientId(patientId, pageable);
    }
    
    @Override
    public Page<Appointment> getAppointmentsByDoctor(Long doctorId, Pageable pageable) {
        return appointmentRepository.findByDoctorId(doctorId, pageable);
    }
    
    @Override
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAppointmentDateBetween(start, end);
    }
    
    @Override
    public List<Appointment> getTodayAppointments() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return appointmentRepository.findByAppointmentDateBetween(start, end);
    }
    
    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime) {
        return !appointmentRepository.existsByDoctorIdAndAppointmentDateAndStatusNotCancelled(doctorId, dateTime);
    }
    
    @Override
    public long countAppointmentsByStatus(String status) {
        return appointmentRepository.countByStatus(status);
    }
}
