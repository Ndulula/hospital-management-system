package com.hospital.management.controller;

import com.hospital.management.dto.request.BillRequest;
import com.hospital.management.dto.response.ApiResponse;
import com.hospital.management.dto.response.BillResponse;
import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.Bill;
import com.hospital.management.entity.Patient;
import com.hospital.management.exception.InvalidRequestException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.service.AppointmentService;
import com.hospital.management.service.BillService;
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

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController extends BaseController {

    private final BillService billService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final EntityToDtoMapper mapper;

    @PostMapping
    public ResponseEntity<ApiResponse<BillResponse>> createBill(@Valid @RequestBody BillRequest request) {
        Patient patient = patientService.getPatientById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", request.getPatientId()));

        Appointment appointment = null;
        if (request.getAppointmentId() != null) {
            appointment = appointmentService.getAppointmentById(request.getAppointmentId());
            if (!appointment.getPatient().getId().equals(patient.getId())) {
                throw new InvalidRequestException("The selected appointment does not belong to this patient");
            }
        }

        Bill bill = new Bill();
        bill.setPatient(patient);
        bill.setAppointment(appointment);
        bill.setConsultationFee(request.getConsultationFee());
        bill.setLaboratoryCharges(request.getLaboratoryCharges());
        bill.setMedicationCharges(request.getMedicationCharges());
        bill.setOtherServices(request.getOtherServices());
        bill.setDiscountAmount(request.getDiscountAmount());
        bill.setNotes(request.getNotes());

        Bill created = billService.createBill(bill);
        return createdResponse("Bill created successfully", mapper.mapToBillResponse(created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BillResponse>>> getAllBills(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BillResponse> bills = billService.getAllBills(pageable).map(mapper::mapToBillResponse);
        return successResponse("Bills retrieved", bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BillResponse>> getBill(@PathVariable Long id) {
        Bill bill = billService.getBillById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));
        return successResponse("Bill retrieved", mapper.mapToBillResponse(bill));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getBillsByPatient(@PathVariable Long patientId) {
        return successResponse("Patient bills", billService.getBillsByPatient(patientId).stream()
                .map(mapper::mapToBillResponse).toList());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getBillsByStatus(@PathVariable String status) {
        return successResponse("Bills by status", billService.getBillsByStatus(status).stream()
                .map(mapper::mapToBillResponse).toList());
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getPendingBills() {
        return successResponse("Pending bills", billService.getPendingBills().stream()
                .map(mapper::mapToBillResponse).toList());
    }

    @GetMapping("/number/{billNumber}")
    public ResponseEntity<ApiResponse<BillResponse>> getBillByNumber(@PathVariable String billNumber) {
        Bill bill = billService.getBillByNumber(billNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "billNumber", billNumber));
        return successResponse("Bill retrieved", mapper.mapToBillResponse(bill));
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<ApiResponse<Void>> markBillAsPaid(
            @PathVariable Long id, @RequestParam String paymentMethod) {
        billService.markBillAsPaid(id, paymentMethod);
        return successResponse("Bill marked as paid", null);
    }

    @GetMapping("/revenue/total")
    public ResponseEntity<ApiResponse<Double>> getTotalRevenue() {
        return successResponse("Total revenue", billService.getTotalRevenue());
    }

    @GetMapping("/revenue/range")
    public ResponseEntity<ApiResponse<Double>> getRevenueBetween(
            @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return successResponse("Revenue for date range", billService.getRevenueBetween(start, end));
    }
}
