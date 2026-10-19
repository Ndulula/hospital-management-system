package com.hospital.management.controller;

import com.hospital.management.dto.request.DoctorRequest;
import com.hospital.management.dto.response.ApiResponse;
import com.hospital.management.dto.response.DoctorResponse;
import com.hospital.management.entity.Department;
import com.hospital.management.entity.Doctor;
import com.hospital.management.service.DepartmentService;
import com.hospital.management.service.DoctorService;
import com.hospital.management.util.EntityToDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController extends BaseController {
    
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final EntityToDtoMapper mapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(
            @Valid @RequestBody DoctorRequest request) {
        Department department = departmentService.getDepartmentById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctorId());
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setDepartment(department);
        doctor.setAvailability(request.getAvailability());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setQualifications(request.getQualifications());
        
        Doctor created = doctorService.createDoctor(doctor);
        DoctorResponse response = mapper.mapToDoctorResponse(created);
        return createdResponse("Doctor created successfully", response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctor(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        DoctorResponse response = mapper.mapToDoctorResponse(doctor);
        return successResponse("Doctor retrieved", response);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> getAllDoctors(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Doctor> doctors = doctorService.getAllDoctors(pageable);
        Page<DoctorResponse> responses = doctors.map(mapper::mapToDoctorResponse);
        return successResponse("Doctors retrieved", responses);
    }
    
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsBySpecialization(
            @PathVariable String specialization) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
        List<DoctorResponse> responses = doctors.stream()
                .map(mapper::mapToDoctorResponse)
                .collect(Collectors.toList());
        return successResponse("Doctors by specialization", responses);
    }
    
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsByDepartment(
            @PathVariable Long departmentId) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartment(departmentId);
        List<DoctorResponse> responses = doctors.stream()
                .map(mapper::mapToDoctorResponse)
                .collect(Collectors.toList());
        return successResponse("Doctors by department", responses);
    }
    
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAvailableDoctors() {
        List<Doctor> doctors = doctorService.getAvailableDoctors();
        List<DoctorResponse> responses = doctors.stream()
                .map(mapper::mapToDoctorResponse)
                .collect(Collectors.toList());
        return successResponse("Available doctors", responses);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequest request) {
        Department department = departmentService.getDepartmentById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctorId());
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setDepartment(department);
        doctor.setAvailability(request.getAvailability());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setQualifications(request.getQualifications());
        
        Doctor updated = doctorService.updateDoctor(id, doctor);
        DoctorResponse response = mapper.mapToDoctorResponse(updated);
        return successResponse("Doctor updated", response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return noContentResponse();
    }
}