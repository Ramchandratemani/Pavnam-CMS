package com.pavnam.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.pavnam.model.SuperAdmin;


public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    SuperAdmin findByEmail(String email);
}