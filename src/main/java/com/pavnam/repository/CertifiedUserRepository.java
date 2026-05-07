package com.pavnam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pavnam.model.CertifiedUser;


public interface CertifiedUserRepository extends JpaRepository<CertifiedUser, Long> {
    CertifiedUser findByEmail(String email);
    List<CertifiedUser> findByEnrolledBy(String enrolledBy);
    List<CertifiedUser> findByCreatedAtAfter(LocalDate date);


    @Query("SELECT COUNT(c) FROM CertifiedUser c WHERE c.dealer.id = :dealerId")
    Integer countByDealerId(@Param("dealerId") Long dealerId);

    long countByEnrolledBy(String enrolledBy); 

// @Query("SELECT COUNT(c) FROM CertifiedUser c WHERE YEAR(c.createdAt) = YEAR(CURRENT_DATE) AND MONTH(c.createdAt) = MONTH(CURRENT_DATE)")
// long countCertifiedUsersThisMonth();
    
    @Query("SELECT COUNT(c) FROM CertifiedUser c WHERE YEAR(c.createdAt) = :year AND MONTH(c.createdAt) = :month")
    long countCertifiedUsersByMonth(@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT COUNT(c) FROM CertifiedUser c WHERE c.dealer.id = :dealerId AND YEAR(c.createdAt) = :year AND MONTH(c.createdAt) = :month")
    long countCertifiedUsersByDealerForMonth(@Param("dealerId") Long dealerId, @Param("year") int year, @Param("month") int month);

    // @Query("SELECT c FROM CertifiedUser c WHERE c.createdAt <= :expiryThreshold")
    // List<CertifiedUser> findExpiringCertificates(@Param("expiryThreshold") LocalDate expiryThreshold);

    // List<CertifiedUser> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT c FROM CertifiedUser c WHERE c.expiryDate <= :expiryThreshold")
    List<CertifiedUser> findExpiringCertificates(@Param("expiryThreshold") LocalDate expiryThreshold);
    

}
