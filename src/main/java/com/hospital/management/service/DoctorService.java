package com.hospital.management.service;

import com.hospital.management.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    
    Doctor createDoctor(Doctor doctor);
    
    Doctor updateDoctor(Long id, Doctor doctor);
    
    void deleteDoctor(Long id);
    
    Optional<Doctor> getDoctorById(Long id);
    
    Optional<Doctor> getDoctorByDoctorId(String doctorId);
    
    List<Doctor> getDoctorsBySpecialization(String specialization);
    
    Page<Doctor> getDoctorsBySpecialization(String specialization, Pageable pageable);
    
    List<Doctor> getDoctorsByDepartment(Long departmentId);
    
    List<Doctor> searchDoctors(String searchTerm);
    
    Page<Doctor> getAllDoctors(Pageable pageable);
    
    List<Doctor> getAvailableDoctors();
    
    boolean existsByDoctorId(String doctorId);
    
    long countDoctorsByDepartment(Long departmentId);
}