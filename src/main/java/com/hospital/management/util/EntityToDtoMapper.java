package com.hospital.management.util;

import com.hospital.management.dto.response.*;
import com.hospital.management.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityToDtoMapper {
    
    public PatientResponse mapToPatientResponse(Patient patient) {
        if (patient == null) return null;
        
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setPatientId(patient.getPatientId());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setBloodGroup(patient.getBloodGroup());
        response.setAddress(patient.getAddress());
        response.setPhoneNumber(patient.getPhoneNumber());
        response.setEmail(patient.getEmail());
        response.setEmergencyContact(patient.getEmergencyContact());
        response.setEmergencyPhone(patient.getEmergencyPhone());
        response.setMedicalHistory(patient.getMedicalHistory());
        response.setActive(patient.isActive());
        response.setCreatedAt(patient.getCreatedAt());
        response.setUpdatedAt(patient.getUpdatedAt());
        return response;
    }
    
    public DoctorResponse mapToDoctorResponse(Doctor doctor) {
        if (doctor == null) return null;
        
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setDoctorId(doctor.getDoctorId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setEmail(doctor.getEmail());
        response.setPhoneNumber(doctor.getPhoneNumber());
        response.setSpecialization(doctor.getSpecialization());
        response.setDepartment(mapToDepartmentResponse(doctor.getDepartment()));
        response.setAvailability(doctor.getAvailability());
        response.setConsultationFee(doctor.getConsultationFee());
        response.setQualifications(doctor.getQualifications());
        response.setActive(doctor.isActive());
        response.setCreatedAt(doctor.getCreatedAt());
        response.setUpdatedAt(doctor.getUpdatedAt());
        return response;
    }
    
    public DepartmentResponse mapToDepartmentResponse(Department department) {
        if (department == null) return null;
        
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setDescription(department.getDescription());
        response.setHeadOfDepartment(department.getHeadOfDepartment());
        response.setPhoneNumber(department.getPhoneNumber());
        response.setLocation(department.getLocation());
        response.setActive(department.isActive());
        response.setCreatedAt(department.getCreatedAt());
        response.setUpdatedAt(department.getUpdatedAt());
        return response;
    }
    
    public AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
        if (appointment == null) return null;
        
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setStatus(appointment.getStatus());
        response.setNotes(appointment.getNotes());
        response.setPatient(mapToPatientResponse(appointment.getPatient()));
        response.setDoctor(mapToDoctorResponse(appointment.getDoctor()));
        response.setActive(appointment.isActive());
        response.setCreatedAt(appointment.getCreatedAt());
        response.setUpdatedAt(appointment.getUpdatedAt());
        return response;
    }
    
    public BillResponse mapToBillResponse(Bill bill) {
        if (bill == null) return null;
        
        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setBillNumber(bill.getBillNumber());
        response.setConsultationFee(bill.getConsultationFee());
        response.setLaboratoryCharges(bill.getLaboratoryCharges());
        response.setMedicationCharges(bill.getMedicationCharges());
        response.setOtherServices(bill.getOtherServices());
        response.setTotalAmount(bill.getTotalAmount());
        response.setTaxAmount(bill.getTaxAmount());
        response.setDiscountAmount(bill.getDiscountAmount());
        response.setFinalAmount(bill.getFinalAmount());
        response.setStatus(bill.getStatus());
        response.setPaymentMethod(bill.getPaymentMethod());
        response.setPaymentDate(bill.getPaymentDate());
        response.setNotes(bill.getNotes());
        response.setPatient(mapToPatientResponse(bill.getPatient()));
        response.setAppointment(mapToAppointmentResponse(bill.getAppointment()));
        response.setActive(bill.isActive());
        response.setCreatedAt(bill.getCreatedAt());
        response.setUpdatedAt(bill.getUpdatedAt());
        return response;
    }
    
    public UserResponse mapToUserResponse(User user) {
        if (user == null) return null;
        
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRoles(user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet()));
        response.setEnabled(user.isEnabled());
        response.setLastLogin(user.getLastLogin());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    public NurseResponse mapToNurseResponse(Nurse nurse) {
    if (nurse == null) return null;
    
    NurseResponse response = new NurseResponse();
    response.setId(nurse.getId());
    response.setNurseId(nurse.getNurseId());
    response.setFirstName(nurse.getFirstName());
    response.setLastName(nurse.getLastName());
    response.setEmail(nurse.getEmail());
    response.setPhoneNumber(nurse.getPhoneNumber());
    response.setDepartment(mapToDepartmentResponse(nurse.getDepartment()));
    response.setShift(nurse.getShift());
    response.setQualifications(nurse.getQualifications());
    response.setActive(nurse.isActive());
    response.setCreatedAt(nurse.getCreatedAt());
    response.setUpdatedAt(nurse.getUpdatedAt());
    return response;
    }
}
