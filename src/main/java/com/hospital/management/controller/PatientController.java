package com.hospital.management.controller;

import com.hospital.management.dto.request.PatientRequest;
import com.hospital.management.dto.response.ApiResponse;
import com.hospital.management.dto.response.PatientResponse;
import com.hospital.management.entity.Patient;
import com.hospital.management.exception.DuplicateResourceException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.service.PatientService;
import com.hospital.management.util.EntityToDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController extends BaseController {
    
    private final PatientService patientService;
    private final EntityToDtoMapper mapper;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(@Valid @RequestBody PatientRequest request) {
        // Check if patient already exists
        if (patientService.existsByPatientId(request.getPatientId())) {
            throw new DuplicateResourceException("Patient", "patientId", request.getPatientId());
        }
        
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setAddress(request.getAddress());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setEmail(request.getEmail());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setEmergencyPhone(request.getEmergencyPhone());
        patient.setMedicalHistory(request.getMedicalHistory());
        
        Patient created = patientService.createPatient(patient);
        PatientResponse response = mapper.mapToPatientResponse(created);
        return createdResponse("Patient created successfully", response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
        PatientResponse response = mapper.mapToPatientResponse(patient);
        return successResponse("Patient retrieved successfully", response);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> getAllPatients(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Patient> patients = patientService.getAllPatients(pageable);
        Page<PatientResponse> responses = patients.map(mapper::mapToPatientResponse);
        return successResponse("Patients retrieved successfully", responses);
    }
    
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> searchPatients(
            @RequestParam String searchTerm) {
        List<Patient> patients = patientService.searchPatients(searchTerm);
        List<PatientResponse> responses = patients.stream()
                .map(mapper::mapToPatientResponse)
                .collect(Collectors.toList());
        return successResponse("Search results retrieved", responses);
    }
    
    @GetMapping("/patient-id/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientByPatientId(
            @PathVariable String patientId) {
        Patient patient = patientService.getPatientByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "patientId", patientId));
        PatientResponse response = mapper.mapToPatientResponse(patient);
        return successResponse("Patient retrieved successfully", response);
    }
    
    @GetMapping("/blood-group/{bloodGroup}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getPatientsByBloodGroup(
            @PathVariable String bloodGroup) {
        List<Patient> patients = patientService.getPatientsByBloodGroup(bloodGroup);
        List<PatientResponse> responses = patients.stream()
                .map(mapper::mapToPatientResponse)
                .collect(Collectors.toList());
        return successResponse("Patients retrieved by blood group", responses);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request) {
        // Check if patient exists
        if (!patientService.existsById(id)) {
            throw new ResourceNotFoundException("Patient", "id", id);
        }
        
        // Check if patientId is being changed and already exists
        Patient existing = patientService.getPatientById(id).get();
        if (!existing.getPatientId().equals(request.getPatientId()) && 
            patientService.existsByPatientId(request.getPatientId())) {
            throw new DuplicateResourceException("Patient", "patientId", request.getPatientId());
        }
        
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setAddress(request.getAddress());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setEmail(request.getEmail());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setEmergencyPhone(request.getEmergencyPhone());
        patient.setMedicalHistory(request.getMedicalHistory());
        
        Patient updated = patientService.updatePatient(id, patient);
        PatientResponse response = mapper.mapToPatientResponse(updated);
        return successResponse("Patient updated successfully", response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (!patientService.existsById(id)) {
            throw new ResourceNotFoundException("Patient", "id", id);
        }
        patientService.deletePatient(id);
        return noContentResponse();
    }
    
    @GetMapping("/count/gender/{gender}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ApiResponse<Long>> countPatientsByGender(@PathVariable String gender) {
        long count = patientService.countPatientsByGender(gender);
        return successResponse("Patient count by gender", count);
    }
}