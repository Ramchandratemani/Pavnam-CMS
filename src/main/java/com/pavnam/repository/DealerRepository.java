package com.pavnam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.pavnam.model.Dealers;

public interface DealerRepository extends JpaRepository<Dealers, Long> {
    Dealers findByEmail(String email);
    Optional<Dealers> findByUsername(String username);

}