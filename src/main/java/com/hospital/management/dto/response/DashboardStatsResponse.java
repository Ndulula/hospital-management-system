package com.hospital.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    
    private long totalPatients;
    private long totalDoctors;
    private long totalNurses;
    private long totalStaff;
    private long todayAppointments;
    private long upcomingAppointments;
    private double totalRevenue;
    
    public DashboardStatsResponse(long totalPatients, long totalDoctors, long totalNurses,
                                  long todayAppointments, long upcomingAppointments, double totalRevenue) {
        this.totalPatients = totalPatients;
        this.totalDoctors = totalDoctors;
        this.totalNurses = totalNurses;
        this.totalStaff = totalDoctors + totalNurses;
        this.todayAppointments = todayAppointments;
        this.upcomingAppointments = upcomingAppointments;
        this.totalRevenue = totalRevenue;
    }
}