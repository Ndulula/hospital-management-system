package com.hospital.management.service;

import com.hospital.management.entity.Nurse;

import java.util.List;
import java.util.Optional;

public interface NurseService {
    
    Nurse createNurse(Nurse nurse);
    
    Nurse updateNurse(Long id, Nurse nurse);
    
    void deleteNurse(Long id);
    
    Optional<Nurse> getNurseById(Long id);
    
    Optional<Nurse> getNurseByNurseId(String nurseId);
    
    List<Nurse> getNursesByShift(String shift);
    
    List<Nurse> getNursesByDepartment(Long departmentId);
    
    List<Nurse> searchNurses(String searchTerm);
    
    List<Nurse> getAllNurses();
    
    boolean existsByNurseId(String nurseId);
    
    long countNursesByShift(String shift);
}