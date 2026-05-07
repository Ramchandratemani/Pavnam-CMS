package com.pavnam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavnam.model.CertifiedUser;
import com.pavnam.repository.CertifiedUserRepository;

@Service
public class CertifiedUserService {

    @Autowired
    private CertifiedUserRepository certifiedUserRepository;


    // Get User by ID
    public CertifiedUser getUserById(Long id) {
        return certifiedUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Delete User
    public void deleteUser(Long id) {
        certifiedUserRepository.deleteById(id);
    }

    public long getTotalCertifiedUsers(String dealerId) {
        return certifiedUserRepository.countByEnrolledBy(dealerId);
    }

    public int getCertifiedUserCountByDealerId(Long dealerId) {
        Integer count = certifiedUserRepository.countByDealerId(dealerId);
        return count != null ? count : 0;
    }

    // public long getCertifiedUsersThisMonth() {
    //     return certifiedUserRepository.countCertifiedUsersThisMonth();
    // }

    public long getCertifiedUsersByDealerForMonth(Long dealerId, int year, int month) {
        return certifiedUserRepository.countCertifiedUsersByDealerForMonth(dealerId, year, month);
    }
    
    public CertifiedUserService(CertifiedUserRepository certifiedUserRepository) {
        this.certifiedUserRepository = certifiedUserRepository;
    }

    // public List<CertifiedUser> getExpiringCertificates() {
    //     LocalDate expiryThreshold = LocalDate.now().minusYears(1).plusDays(30);
    //     return certifiedUserRepository.findExpiringCertificates(expiryThreshold);
    // }

public List<CertifiedUser> getExpiringCertificates() {
    LocalDate expiryThreshold = LocalDate.now(); // Expiring today
    return certifiedUserRepository.findExpiringCertificates(expiryThreshold);
}

    
    public void renewCertifiedUser(Long userId) {
        Optional<CertifiedUser> userOpt = certifiedUserRepository.findById(userId);
        if (userOpt.isPresent()) {
            CertifiedUser user = userOpt.get();
            user.renewCertificate();
            certifiedUserRepository.save(user);
        } else {
            throw new RuntimeException("Certified User not found with ID: " + userId);
        }
    }

    public List<CertifiedUser> getAllCertifiedUsers() {
        return certifiedUserRepository.findAll();
    }

    // public List<CertifiedUser> getExpiringSoonUsers() {
    //     LocalDate today = LocalDate.now();
    //     LocalDate nextMonth = today.plusMonths(1);
    //     return certifiedUserRepository.findByExpiryDateBetween(today, nextMonth);
    // }

    
}
