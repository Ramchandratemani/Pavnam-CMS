package com.pavnam.service;

// import java.time.DayOfWeek;
// import java.time.LocalDate;
// import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavnam.repository.CertifiedUserRepository;

@Service
public class DashboardService {

    @Autowired
    private CertifiedUserRepository certifiedUserRepository;

    public long getTotalCertifiedUsers() {
        return certifiedUserRepository.count();
    }

    public long getCertifiedUsersByMonth(int year, int month) {
        return certifiedUserRepository.countCertifiedUsersByMonth(year, month);
    }

}
    // public long getCertifiedUsersLastWeek() {
    //     LocalDate lastWeekStart = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);
    //     LocalDate lastWeekEnd = lastWeekStart.plusDays(6);
    //     return certifiedUserRepository.countByCreatedAtBetween(lastWeekStart, lastWeekEnd);
    // }

    // public double getPercentageIncrease() {
    //     long lastWeek = getCertifiedUsersLastWeek();
    //     long currentWeek = getTotalCertifiedUsers();

    //     if (lastWeek == 0) {
    //         return currentWeek > 0 ? 100.0 : 0.0;
    //     }

    //     return ((double) (currentWeek - lastWeek) / lastWeek) * 100;
    // }

    // public long getCertifiedUsersThisMonth() {
    //     LocalDate now = LocalDate.now();
    //     LocalDate startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
    //     LocalDate endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1); // Include the last day
        
    //     return certifiedUserRepository.countByCreatedAtBetween(startOfMonth, endOfMonth);
    // }


