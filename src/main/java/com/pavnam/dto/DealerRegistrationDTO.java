package com.pavnam.dto;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Email;

public class DealerRegistrationDTO {
    private String username;
    
    @Email(message = "{errors.invalid_email}")
    private String email;
    
    private String fullName;
    private String mobile;
    private String city;
    private String state;
    private String address;
    private String govtId;
    private String govtIdNumber;
    private String password;
    private MultipartFile profilePicFile;
    private MultipartFile govtIdFile;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getProfilePicFile() {
        return profilePicFile;
    }

    public void setProfilePicFile(MultipartFile profilePicFile) {
        this.profilePicFile = profilePicFile;
    }

    public MultipartFile getGovtIdFile() {
        return govtIdFile;
    }

    public void setGovtIdFile(MultipartFile govtIdFile) {
        this.govtIdFile = govtIdFile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}




