package com.pavnam.model;

import java.util.Collection;
import java.util.List;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;



@Entity
@Table(name = "rlusers")
public class Users implements UserDetails {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long user_id;

  @Column
  private String name;

  @Column(unique = true, length = 60)
  @Email(message = "{errors.invalid_email}")
  private String email;

  @Column
  private String password;

  @Column
  private String resetToken;




@ManyToMany( fetch = FetchType.EAGER ,cascade = { CascadeType.ALL })
  @JoinTable(name = "user_role", joinColumns = {
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
      @JoinColumn(name = "role_id", referencedColumnName = "role_id") })
  private List<Role> roles;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @JoinTable(name = "user_dealer", joinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
          @JoinColumn(name = "dealer_id", referencedColumnName = "id")})
  private List<Dealers> dealers;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @JoinTable(name = "superadmin_user", joinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
          @JoinColumn(name = "superadmin_id", referencedColumnName = "id")})
  private List<SuperAdmin> superAdmins;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @JoinTable(name = "user_certified", joinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
          @JoinColumn(name = "certified_user_id", referencedColumnName = "id")})
  private List<CertifiedUser> certifiedUsers;


 public Users() {
  super();
 }

 public Users(long user_id, String name, @Email(message = "{errors.invalid_email}") String email, String password,
   List<Role> roles, List<Dealers> dealers , List<SuperAdmin> superAdmins, List<CertifiedUser> certifiedUsers) {
  super();
  this.user_id = user_id;
  this.name = name;
  this.email = email;
  this.password = password;
  this.roles = roles;
  this.dealers = dealers;
  this.superAdmins = superAdmins;
  this.certifiedUsers = certifiedUsers;
 }

 public void setRoles(List<Role> roles) {
  this.roles = roles;
 }

 public void setDealers(List<Dealers> dealers) {
  this.dealers = dealers;
}

 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
  String[] userRoles = getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
 }

 @Override
 public String getPassword() {
  return password;
 }

 @Override
 public String getUsername() {
  return email;
 }

 @Override
 public boolean isAccountNonExpired() {
  return true;
 }

 public long getUser_id() {
  return user_id;
 }

 public void setUser_id(long user_id) {
  this.user_id = user_id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public List<Role> getRoles() {
  return roles;
 }

 public List<Dealers> getDealers() {
  return dealers;
}

public List<SuperAdmin> getSuperAdmins() {
  return superAdmins;
}



 public void setPassword(String password) {
  this.password = password;
 }

 @Override
 public boolean isAccountNonLocked() {
  return true;
 }

 @Override
 public boolean isCredentialsNonExpired() {
  return true;
 }

 @Override
 public boolean isEnabled() {
  return true;
 }

public String getResetToken() {
  return resetToken;
}

public void setResetToken(String resetToken) {
  this.resetToken = resetToken;
}
}