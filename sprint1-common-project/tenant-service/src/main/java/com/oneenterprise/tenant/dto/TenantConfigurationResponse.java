package com.oneenterprise.tenant.dto;

import java.util.UUID;

public class TenantConfigurationResponse {

    private UUID id;
    private UUID tenantId;
    private String currency;
    private String dateFormat;
    private boolean emailEnabled;
    private boolean notificationsEnabled;
    private boolean selfServiceEnabled;
    private Integer maxUsers;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
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
