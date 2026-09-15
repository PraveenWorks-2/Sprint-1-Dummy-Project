package com.oneenterprise.dummyproject.platform.superadmin.dto;

public class SuperAdminRequestDto {
    private String fullName;
    private String email;
    private String phoneNumber;

    public SuperAdminRequestDto() {}

    public SuperAdminRequestDto(String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}