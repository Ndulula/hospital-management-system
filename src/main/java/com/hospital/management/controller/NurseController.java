package com.hospital.management.controller;

import com.hospital.management.dto.request.NurseRequest;
import com.hospital.management.dto.response.ApiResponse;
import com.hospital.management.dto.response.NurseResponse;
import com.hospital.management.entity.Department;
import com.hospital.management.entity.Nurse;
import com.hospital.management.service.DepartmentService;
import com.hospital.management.service.NurseService;
import com.hospital.management.util.EntityToDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nurses")
@RequiredArgsConstructor
public class NurseController extends BaseController {
    
    private final NurseService nurseService;
    private final DepartmentService departmentService;
    private final EntityToDtoMapper mapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<NurseResponse>> createNurse(@Valid @RequestBody NurseRequest request) {
        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentService.getDepartmentById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
        }
        
        Nurse nurse = new Nurse();
        nurse.setNurseId(request.getNurseId());
        nurse.setFirstName(request.getFirstName());
        nurse.setLastName(request.getLastName());
        nurse.setEmail(request.getEmail());
        nurse.setPhoneNumber(request.getPhoneNumber());
        nurse.setDepartment(department);
        nurse.setShift(request.getShift());
        nurse.setQualifications(request.getQualifications());
        
        Nurse created = nurseService.createNurse(nurse);
        NurseResponse response = mapper.mapToNurseResponse(created);
        return createdResponse("Nurse created successfully", response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NurseResponse>> getNurse(@PathVariable Long id) {
        Nurse nurse = nurseService.getNurseById(id)
                .orElseThrow(() -> new RuntimeException("Nurse not found"));
        NurseResponse response = mapper.mapToNurseResponse(nurse);
        return successResponse("Nurse retrieved", response);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<NurseResponse>>> getAllNurses() {
        List<Nurse> nurses = nurseService.getAllNurses();
        List<NurseResponse> responses = nurses.stream()
                .map(mapper::mapToNurseResponse)
                .collect(Collectors.toList());
        return successResponse("All nurses retrieved", responses);
    }
    
    @GetMapping("/shift/{shift}")
    public ResponseEntity<ApiResponse<List<NurseResponse>>> getNursesByShift(@PathVariable String shift) {
        List<Nurse> nurses = nurseService.getNursesByShift(shift);
        List<NurseResponse> responses = nurses.stream()
                .map(mapper::mapToNurseResponse)
                .collect(Collectors.toList());
        return successResponse("Nurses by shift", responses);
    }
    
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<NurseResponse>>> getNursesByDepartment(@PathVariable Long departmentId) {
        List<Nurse> nurses = nurseService.getNursesByDepartment(departmentId);
        List<NurseResponse> responses = nurses.stream()
                .map(mapper::mapToNurseResponse)
                .collect(Collectors.toList());
        return successResponse("Nurses by department", responses);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NurseResponse>> updateNurse(
            @PathVariable Long id,
            @Valid @RequestBody NurseRequest request) {
        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentService.getDepartmentById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
        }
        
        Nurse nurse = new Nurse();
        nurse.setNurseId(request.getNurseId());
        nurse.setFirstName(request.getFirstName());
        nurse.setLastName(request.getLastName());
        nurse.setEmail(request.getEmail());
        nurse.setPhoneNumber(request.getPhoneNumber());
        nurse.setDepartment(department);
        nurse.setShift(request.getShift());
        nurse.setQualifications(request.getQualifications());
        
        Nurse updated = nurseService.updateNurse(id, nurse);
        NurseResponse response = mapper.mapToNurseResponse(updated);
        return successResponse("Nurse updated", response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {
        nurseService.deleteNurse(id);
        return noContentResponse();
    }
}