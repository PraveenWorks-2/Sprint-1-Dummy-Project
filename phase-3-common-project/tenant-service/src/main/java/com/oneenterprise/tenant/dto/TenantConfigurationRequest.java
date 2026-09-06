package com.oneenterprise.tenant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TenantConfigurationRequest {

    @NotBlank
    @Size(max = 10)
    private String currency;

    @NotBlank
    @Size(max = 30)
    private String dateFormat;

    private boolean emailEnabled;
    private boolean notificationsEnabled;
    private boolean selfServiceEnabled;

    @Min(1)
    @Max(1000000)
    private Integer maxUsers;

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getDateFormat() { return dateFormat; }
    public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; }
    public boolean isEmailEnabled() { return emailEnabled; }
    public void setEmailEnabled(boolean emailEnabled) { this.emailEnabled = emailEnabled; }
    public boolean isNotificationsEnabled() { return notificationsEnabled; }
    public void setNotificationsEnabled(boolean notificationsEnabled) { this.notificationsEnabled = notificationsEnabled; }
    public boolean isSelfServiceEnabled() { return selfServiceEnabled; }
    public void setSelfServiceEnabled(boolean selfServiceEnabled) { this.selfServiceEnabled = selfServiceEnabled; }
    public Integer getMaxUsers() { return maxUsers; }
    public void setMaxUsers(Integer maxUsers) { this.maxUsers = maxUsers; }
}
