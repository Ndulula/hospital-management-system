package com.hospital.management.repository;

import com.hospital.management.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    
    /**
     * Find bill by bill number
     */
    Optional<Bill> findByBillNumber(String billNumber);
    
    /**
     * Find bills by patient ID
     */
    List<Bill> findByPatientId(Long patientId);
    
    /**
     * Find bills by status
     */
    List<Bill> findByStatus(String status);
    
    /**
     * Find bills by date range
     */
    List<Bill> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Find pending bills
     */
    @Query("SELECT b FROM Bill b WHERE b.status = 'PENDING'")
    List<Bill> findPendingBills();
    
    /**
     * Calculate total revenue
     */
    @Query("SELECT SUM(b.finalAmount) FROM Bill b WHERE b.status = 'PAID'")
    Double calculateTotalRevenue();
    
    /**
     * Calculate revenue for a specific period
     */
    @Query("SELECT SUM(b.finalAmount) FROM Bill b WHERE b.status = 'PAID' AND b.paymentDate BETWEEN :start AND :end")
    Double calculateRevenueBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    /**
     * Count bills by status
     */
    @Query("SELECT COUNT(b) FROM Bill b WHERE b.status = :status")
    long countByStatus(@Param("status") String status);
}