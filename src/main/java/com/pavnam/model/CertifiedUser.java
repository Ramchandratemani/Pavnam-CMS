package com.pavnam.model;

import java.time.LocalDate;
import java.util.List;


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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;


@Entity
@Table(name = "certified_user")
public class CertifiedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    @Email(message = "{errors.invalid_email}")
    private String email;

    private String businessType;

    @Column(unique = true, nullable = false)
    private String contactNumber;

    @Column(unique = true, nullable = false)
    private String businessAddress;

    @Column(unique = true, nullable = false)
    private String ownerName;

    private String fssaiLicenseNumber;

    @Column(unique = true, nullable = false)
    private String govtId;

    @Column(unique = true, nullable = false)
    private String govtIdNumber;

    @Column(name = "govt_id_file_path")
    private String govtIdFilePath;  

    private String website;
    private String foodProductSize;
    private String manufacturingUnit;

    @Column(nullable = false)
    private String password;
    private String enrolledBy;
    private String role = "CertifiedUser";

    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;


    @Column(name = "renew_date")
    private LocalDate renewDate;

    @Column(name = "renewal_status")
    private Boolean renewalStatus = false;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.expiryDate = this.createdAt.plusYears(1);
    }   

    public void renewCertificate() {
        this.renewDate = LocalDate.now();
        this.expiryDate = this.renewDate.plusYears(1);
        this.renewalStatus = true;
    }
    
    
    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = true)  // Optional dealer link
    private Dealers dealer;  // Store dealer ID


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_certified", joinColumns = {
            @JoinColumn(name = "certified_user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    private List<Users> users;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "role_certified", joinColumns = {
            @JoinColumn(name = "certified_user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private List<Role> roles;

    public CertifiedUser() {
        super();
    }

    public CertifiedUser(Long id, String username, String email, String businessType, 
                         String contactNumber, String businessAddress, String ownerName, 
                         String fssaiLicenseNumber, String govtId, String govtIdNumber, 
                         String govtIdFilePath, String website, String foodProductSize, 
                         String manufacturingUnit, Dealers dealer, String enrolledBy, 
                         String role, List<Users> users, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.businessType = businessType;
        this.contactNumber = contactNumber;
        this.businessAddress = businessAddress;
        this.ownerName = ownerName;
        this.fssaiLicenseNumber = fssaiLicenseNumber;
        this.govtId = govtId;
        this.govtIdNumber = govtIdNumber;
        this.govtIdFilePath = govtIdFilePath;
        this.website = website;
        this.foodProductSize = foodProductSize;
        this.manufacturingUnit = manufacturingUnit;
        this.dealer = dealer;
        this.enrolledBy = enrolledBy;
        this.role = role;
        this.users = users;
        this.roles = roles;
        this.createdAt = LocalDate.now();
        this.expiryDate = this.createdAt.plusYears(1);
    }
   

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getGovtIdFilePath() {
        return govtIdFilePath;
    }

    public void setGovtIdFilePath(String govtIdFilePath) {
        this.govtIdFilePath = govtIdFilePath;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBusinessType() {
        return businessType;
    }
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getBusinessAddress() {
        return businessAddress;
    }
    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getFssaiLicenseNumber() {
        return fssaiLicenseNumber;
    }
    public void setFssaiLicenseNumber(String fssaiLicenseNumber) {
        this.fssaiLicenseNumber = fssaiLicenseNumber;
    }
    public String getGovtId() {
        return govtId;
    }
    public void setGovtId(String govtId) {
        this.govtId = govtId;
    }
    public String getGovtIdNumber() {
        return govtIdNumber;
    }
    public void setGovtIdNumber(String govtIdNumber) {
        this.govtIdNumber = govtIdNumber;
    }
    // public byte[] getGovtIdFile() {
    //     return govtIdFile;
    // }
    // public void setGovtIdFile(byte[] govtIdFile) {
    //     this.govtIdFile = govtIdFile;
    // }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getFoodProductSize() {
        return foodProductSize;
    }
    public void setFoodProductSize(String foodProductSize) {
        this.foodProductSize = foodProductSize;
    }
    public String getManufacturingUnit() {
        return manufacturingUnit;
    }
    public void setManufacturingUnit(String manufacturingUnit) {
        this.manufacturingUnit = manufacturingUnit;
    }

    public String getEnrolledBy() {
        return enrolledBy;
    }
    public void setEnrolledBy(String enrolledBy) {
        this.enrolledBy = enrolledBy;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(LocalDate renewDate) {
        this.renewDate = renewDate;
    }

    public Boolean getRenewalStatus() {
        return renewalStatus;
    }

    public void setRenewalStatus(Boolean renewalStatus) {
        this.renewalStatus = renewalStatus;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
        
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Dealers getDealer() {
        return dealer;
    }

    public void setDealer(Dealers dealer) {
        this.dealer = dealer;
    }
}

