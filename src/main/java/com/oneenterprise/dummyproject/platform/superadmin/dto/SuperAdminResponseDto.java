package com.oneenterprise.dummyproject.platform.superadmin.dto;

import java.time.LocalDateTime;

public class SuperAdminResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public SuperAdminResponseDto() {}

    public SuperAdminResponseDto(Long id, String fullName, String email, String phoneNumber, String role, Boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}