package com.pavnam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavnam.model.Role;



public interface RoleRepository extends JpaRepository<Role, Long>{
 
 List<Role> findByName (String name);

}