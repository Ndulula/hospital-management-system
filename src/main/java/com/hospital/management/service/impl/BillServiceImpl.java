package com.hospital.management.service.impl;

import com.hospital.management.entity.Bill;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.BillRepository;
import com.hospital.management.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BillServiceImpl implements BillService {
    
    private final BillRepository billRepository;
    
    @Override
    public Bill createBill(Bill bill) {
        // Generate bill number
        bill.setBillNumber("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        // Calculate totals
        double consultation = bill.getConsultationFee() != null ? bill.getConsultationFee() : 0.0;
        double lab = bill.getLaboratoryCharges() != null ? bill.getLaboratoryCharges() : 0.0;
        double medication = bill.getMedicationCharges() != null ? bill.getMedicationCharges() : 0.0;
        double other = bill.getOtherServices() != null ? bill.getOtherServices() : 0.0;
        
        double total = consultation + lab + medication + other;
        bill.setTotalAmount(total);
        
        double tax = total * 0.15; // 15% tax
        bill.setTaxAmount(tax);
        
        double discount = bill.getDiscountAmount() != null ? bill.getDiscountAmount() : 0.0;
        bill.setFinalAmount(total + tax - discount);
        
        bill.setStatus("PENDING");
        return billRepository.save(bill);
    }
    
    @Override
    public Bill updateBill(Long id, Bill bill) {
        Bill existing = billRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
        existing.setConsultationFee(bill.getConsultationFee());
        existing.setLaboratoryCharges(bill.getLaboratoryCharges());
        existing.setMedicationCharges(bill.getMedicationCharges());
        existing.setOtherServices(bill.getOtherServices());
        existing.setDiscountAmount(bill.getDiscountAmount());
        existing.setNotes(bill.getNotes());
        return billRepository.save(existing);
    }
    
    @Override
    public void deleteBill(Long id) {
        if (!billRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bill not found with id: " + id);
        }
        billRepository.deleteById(id);
    }
    
    @Override
    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }
    
    @Override
    public Optional<Bill> getBillByNumber(String billNumber) {
        return billRepository.findByBillNumber(billNumber);
    }

    @Override
    public Page<Bill> getAllBills(Pageable pageable) {
        return billRepository.findAll(pageable);
    }
    
    @Override
    public List<Bill> getBillsByPatient(Long patientId) {
        return billRepository.findByPatientId(patientId);
    }
    
    @Override
    public List<Bill> getBillsByStatus(String status) {
        return billRepository.findByStatus(status);
    }
    
    @Override
    public List<Bill> getBillsByDateRange(LocalDateTime start, LocalDateTime end) {
        return billRepository.findByPaymentDateBetween(start, end);
    }
    
    @Override
    public List<Bill> getPendingBills() {
        return billRepository.findPendingBills();
    }
    
    @Override
    public void markBillAsPaid(Long id, String paymentMethod) {
        Bill bill = billRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
        bill.setStatus("PAID");
        bill.setPaymentMethod(paymentMethod);
        bill.setPaymentDate(LocalDateTime.now());
        billRepository.save(bill);
    }
    
    @Override
    public Double getTotalRevenue() {
        Double revenue = billRepository.calculateTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }
    
    @Override
    public Double getRevenueBetween(LocalDateTime start, LocalDateTime end) {
        Double revenue = billRepository.calculateRevenueBetween(start, end);
        return revenue != null ? revenue : 0.0;
    }
    
    @Override
    public long countBillsByStatus(String status) {
        return billRepository.countByStatus(status);
    }
}
