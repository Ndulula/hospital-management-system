package com.hospital.management.service.impl;

import com.hospital.management.entity.Nurse;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.NurseRepository;
import com.hospital.management.service.NurseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NurseServiceImpl implements NurseService {
    
    private final NurseRepository nurseRepository;
    
    @Override
    public Nurse createNurse(Nurse nurse) {
        if (existsByNurseId(nurse.getNurseId())) {
            throw new IllegalArgumentException("Nurse with ID " + nurse.getNurseId() + " already exists");
        }
        return nurseRepository.save(nurse);
    }
    
    @Override
    public Nurse updateNurse(Long id, Nurse nurse) {
        Nurse existing = nurseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nurse not found with id: " + id));
        existing.setFirstName(nurse.getFirstName());
        existing.setLastName(nurse.getLastName());
        existing.setEmail(nurse.getEmail());
        existing.setPhoneNumber(nurse.getPhoneNumber());
        existing.setDepartment(nurse.getDepartment());
        existing.setShift(nurse.getShift());
        existing.setQualifications(nurse.getQualifications());
        return nurseRepository.save(existing);
    }
    
    @Override
    public void deleteNurse(Long id) {
        if (!nurseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nurse not found with id: " + id);
        }
        nurseRepository.deleteById(id);
    }
    
    @Override
    public Optional<Nurse> getNurseById(Long id) {
        return nurseRepository.findById(id);
    }
    
    @Override
    public Optional<Nurse> getNurseByNurseId(String nurseId) {
        return nurseRepository.findByNurseId(nurseId);
    }
    
    @Override
    public List<Nurse> getNursesByShift(String shift) {
        return nurseRepository.findByShift(shift);
    }
    
    @Override
    public List<Nurse> getNursesByDepartment(Long departmentId) {
        return nurseRepository.findByDepartmentId(departmentId);
    }
    
    @Override
    public List<Nurse> searchNurses(String searchTerm) {
        return nurseRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
    }
    
    @Override
    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }
    
    @Override
    public boolean existsByNurseId(String nurseId) {
        return nurseRepository.existsByNurseId(nurseId);
    }
    
    @Override
    public long countNursesByShift(String shift) {
        return nurseRepository.countByShift(shift);
    }
}