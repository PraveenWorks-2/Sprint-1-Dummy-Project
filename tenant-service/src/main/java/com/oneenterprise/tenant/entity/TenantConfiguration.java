package com.oneenterprise.tenant.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tenant_configurations", indexes =
        @Index(name = "idx_tenant_config_tenant_id", columnList = "tenant_id", unique = true))
public class TenantConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "tenant_id", nullable = false, unique = true)
    private UUID tenantId;
    @Column(nullable = false, length = 10) private String currency = "INR";
    @Column(name = "date_format", nullable = false, length = 30) private String dateFormat = "dd-MM-yyyy";
    @Column(name = "email_enabled", nullable = false) private boolean emailEnabled = true;
    @Column(name = "notifications_enabled", nullable = false) private boolean notificationsEnabled = true;
    @Column(name = "self_service_enabled", nullable = false) private boolean selfServiceEnabled = true;
    @Column(name = "max_users", nullable = false) private Integer maxUsers = 100;
    @Column(name = "data_retention_days", nullable = false, columnDefinition = "integer default 365") private Integer dataRetentionDays = 365;
    @Column(name = "max_storage_mb", nullable = false, columnDefinition = "integer default 10240") private Integer maxStorageMb = 10240;
    @Column(name = "session_timeout_minutes", nullable = false, columnDefinition = "integer default 30") private Integer sessionTimeoutMinutes = 30;
    @Column(name = "audit_enabled", nullable = false, columnDefinition = "boolean default true") private boolean auditEnabled = true;
    @Column(name = "backup_enabled", nullable = false, columnDefinition = "boolean default true") private boolean backupEnabled = true;
    @Column(name = "backup_retention_days", nullable = false, columnDefinition = "integer default 30") private Integer backupRetentionDays = 30;
    @Column(name = "password_min_length", nullable = false, columnDefinition = "integer default 12") private Integer passwordMinLength = 12;
    @Column(name = "default_role", nullable = false, length = 50, columnDefinition = "varchar(50) default 'USER'") private String defaultRole = "USER";

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getDateFormat() { return dateFormat; }
    public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; }
    public boolean isEmailEnabled() { return emailEnabled; }
    public void setEmailEnabled(boolean value) { emailEnabled = value; }
    public boolean isNotificationsEnabled() { return notificationsEnabled; }
    public void setNotificationsEnabled(boolean value) { notificationsEnabled = value; }
    public boolean isSelfServiceEnabled() { return selfServiceEnabled; }
    public void setSelfServiceEnabled(boolean value) { selfServiceEnabled = value; }
    public Integer getMaxUsers() { return maxUsers; }
    public void setMaxUsers(Integer value) { maxUsers = value; }
    public Integer getDataRetentionDays() { return dataRetentionDays; }
    public void setDataRetentionDays(Integer value) { dataRetentionDays = value; }
    public Integer getMaxStorageMb() { return maxStorageMb; }
    public void setMaxStorageMb(Integer value) { maxStorageMb = value; }
    public Integer getSessionTimeoutMinutes() { return sessionTimeoutMinutes; }
    public void setSessionTimeoutMinutes(Integer value) { sessionTimeoutMinutes = value; }
    public boolean isAuditEnabled() { return auditEnabled; }
    public void setAuditEnabled(boolean value) { auditEnabled = value; }
    public boolean isBackupEnabled() { return backupEnabled; }
    public void setBackupEnabled(boolean value) { backupEnabled = value; }
    public Integer getBackupRetentionDays() { return backupRetentionDays; }
    public void setBackupRetentionDays(Integer value) { backupRetentionDays = value; }
    public Integer getPasswordMinLength() { return passwordMinLength; }
    public void setPasswordMinLength(Integer value) { passwordMinLength = value; }
    public String getDefaultRole() { return defaultRole; }
    public void setDefaultRole(String value) { defaultRole = value; }
}
