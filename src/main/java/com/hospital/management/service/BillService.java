package com.hospital.management.service;

import com.hospital.management.entity.Bill;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {
    
    Bill createBill(Bill bill);
    
    Bill updateBill(Long id, Bill bill);
    
    void deleteBill(Long id);
    
    Optional<Bill> getBillById(Long id);
    
    Optional<Bill> getBillByNumber(String billNumber);

    Page<Bill> getAllBills(Pageable pageable);
    
    List<Bill> getBillsByPatient(Long patientId);
    
    List<Bill> getBillsByStatus(String status);
    
    List<Bill> getBillsByDateRange(LocalDateTime start, LocalDateTime end);
    
    List<Bill> getPendingBills();
    
    void markBillAsPaid(Long id, String paymentMethod);
    
    Double getTotalRevenue();
    
    Double getRevenueBetween(LocalDateTime start, LocalDateTime end);
    
    long countBillsByStatus(String status);
}
