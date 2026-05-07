package com.pavnam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavnam.model.Users;


public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String email);

    Users findByResetToken(String token);
}