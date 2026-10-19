package com.hospital.management.controller;

import com.hospital.management.dto.request.AppointmentRequest;
import com.hospital.management.dto.response.ApiResponse;
import com.hospital.management.dto.response.AppointmentResponse;
import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.Patient;
import com.hospital.management.service.AppointmentService;
import com.hospital.management.service.DoctorService;
import com.hospital.management.service.PatientService;
import com.hospital.management.util.EntityToDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController extends BaseController {
    
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final EntityToDtoMapper mapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {
        Patient patient = patientService.getPatientById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorService.getDoctorById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setNotes(request.getNotes());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        
        Appointment created = appointmentService.createAppointment(appointment);
        AppointmentResponse response = mapper.mapToAppointmentResponse(created);
        return createdResponse("Appointment created successfully", response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        AppointmentResponse response = mapper.mapToAppointmentResponse(appointment);
        return successResponse("Appointment retrieved", response);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getAllAppointments(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<AppointmentResponse> appointments = appointmentService.getAllAppointments(pageable)
                .map(mapper::mapToAppointmentResponse);
        return successResponse("Appointments retrieved", appointments);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByPatient(
            @PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        List<AppointmentResponse> responses = appointments.stream()
                .map(mapper::mapToAppointmentResponse)
                .collect(Collectors.toList());
        return successResponse("Patient appointments", responses);
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByDoctor(
            @PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        List<AppointmentResponse> responses = appointments.stream()
                .map(mapper::mapToAppointmentResponse)
                .collect(Collectors.toList());
        return successResponse("Doctor appointments", responses);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByStatus(
            @PathVariable String status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        List<AppointmentResponse> responses = appointments.stream()
                .map(mapper::mapToAppointmentResponse)
                .collect(Collectors.toList());
        return successResponse("Appointments by status", responses);
    }
    
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getTodayAppointments() {
        List<Appointment> appointments = appointmentService.getTodayAppointments();
        List<AppointmentResponse> responses = appointments.stream()
                .map(mapper::mapToAppointmentResponse)
                .collect(Collectors.toList());
        return successResponse("Today's appointments", responses);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDateRange(start, end);
        List<AppointmentResponse> responses = appointments.stream()
                .map(mapper::mapToAppointmentResponse)
                .collect(Collectors.toList());
        return successResponse("Appointments in date range", responses);
    }
    
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return successResponse("Appointment cancelled", null);
    }
    
    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Void>> completeAppointment(@PathVariable Long id) {
        appointmentService.completeAppointment(id);
        return successResponse("Appointment completed", null);
    }
    
    @GetMapping("/check-availability")
    public ResponseEntity<ApiResponse<Boolean>> checkDoctorAvailability(
            @RequestParam Long doctorId,
            @RequestParam LocalDateTime dateTime) {
        boolean available = appointmentService.isDoctorAvailable(doctorId, dateTime);
        return successResponse("Availability check", available);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        // We'll use cancel instead of delete for appointments
        appointmentService.cancelAppointment(id);
        return noContentResponse();
    }
}
