package com.pavnam.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="u_role")
public class Role {
 
 
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
  private long role_id;
 @Column (nullable = false)
 private String name;

 @ManyToMany(mappedBy = "roles")
    private List < Users > users; 
 public List<Users> getUsers() {
  return users;
 }
 public Role() {
  super();
 }
 public Role(long role_id, String name) {
  super();  
  this.role_id = role_id;
  this.name = name;
  
 }
 public long getRole_id() {
  return role_id;
 }
 public void setRole_id(long role_id) {
  this.role_id = role_id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

}
