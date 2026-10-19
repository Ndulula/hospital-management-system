package com.hospital.management.service.impl;

import com.hospital.management.entity.Doctor;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
    
    private final DoctorRepository doctorRepository;
    
    @Override
    public Doctor createDoctor(Doctor doctor) {
        if (existsByDoctorId(doctor.getDoctorId())) {
            throw new IllegalArgumentException("Doctor with ID " + doctor.getDoctorId() + " already exists");
        }
        return doctorRepository.save(doctor);
    }
    
    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existing = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        existing.setFirstName(doctor.getFirstName());
        existing.setLastName(doctor.getLastName());
        existing.setEmail(doctor.getEmail());
        existing.setPhoneNumber(doctor.getPhoneNumber());
        existing.setSpecialization(doctor.getSpecialization());
        existing.setDepartment(doctor.getDepartment());
        existing.setAvailability(doctor.getAvailability());
        existing.setConsultationFee(doctor.getConsultationFee());
        existing.setQualifications(doctor.getQualifications());
        return doctorRepository.save(existing);
    }
    
    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
    
    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
    
    @Override
    public Optional<Doctor> getDoctorByDoctorId(String doctorId) {
        return doctorRepository.findByDoctorId(doctorId);
    }
    
    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }
    
    @Override
    public Page<Doctor> getDoctorsBySpecialization(String specialization, Pageable pageable) {
        return doctorRepository.findBySpecialization(specialization, pageable);
    }
    
    @Override
    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId);
    }
    
    @Override
    public List<Doctor> searchDoctors(String searchTerm) {
        return doctorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
    }
    
    @Override
    public Page<Doctor> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }
    
    @Override
    public List<Doctor> getAvailableDoctors() {
        return doctorRepository.findAvailableDoctors();
    }
    
    @Override
    public boolean existsByDoctorId(String doctorId) {
        return doctorRepository.existsByDoctorId(doctorId);
    }
    
    @Override
    public long countDoctorsByDepartment(Long departmentId) {
        return doctorRepository.countByDepartmentId(departmentId);
    }
}