package com.oneenterprise.tenant.dto;

import jakarta.validation.constraints.*;

public class TenantConfigurationRequest {
    @NotBlank @Size(max = 10) private String currency;
    @NotBlank @Size(max = 30) private String dateFormat;
    private boolean emailEnabled;
    private boolean notificationsEnabled;
    private boolean selfServiceEnabled;
    @NotNull @Min(1) @Max(1000000) private Integer maxUsers;
    @NotNull @Min(1) @Max(3650) private Integer dataRetentionDays;
    @NotNull @Min(100) @Max(10485760) private Integer maxStorageMb;
    @NotNull @Min(5) @Max(1440) private Integer sessionTimeoutMinutes;
    private boolean auditEnabled;
    private boolean backupEnabled;
    @NotNull @Min(1) @Max(3650) private Integer backupRetentionDays;
    @NotNull @Min(8) @Max(128) private Integer passwordMinLength;
    @NotBlank @Size(max = 50) @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_-]*$") private String defaultRole;

    public String getCurrency(){return currency;} public void setCurrency(String v){currency=v;}
    public String getDateFormat(){return dateFormat;} public void setDateFormat(String v){dateFormat=v;}
    public boolean isEmailEnabled(){return emailEnabled;} public void setEmailEnabled(boolean v){emailEnabled=v;}
    public boolean isNotificationsEnabled(){return notificationsEnabled;} public void setNotificationsEnabled(boolean v){notificationsEnabled=v;}
    public boolean isSelfServiceEnabled(){return selfServiceEnabled;} public void setSelfServiceEnabled(boolean v){selfServiceEnabled=v;}
    public Integer getMaxUsers(){return maxUsers;} public void setMaxUsers(Integer v){maxUsers=v;}
    public Integer getDataRetentionDays(){return dataRetentionDays;} public void setDataRetentionDays(Integer v){dataRetentionDays=v;}
    public Integer getMaxStorageMb(){return maxStorageMb;} public void setMaxStorageMb(Integer v){maxStorageMb=v;}
    public Integer getSessionTimeoutMinutes(){return sessionTimeoutMinutes;} public void setSessionTimeoutMinutes(Integer v){sessionTimeoutMinutes=v;}
    public boolean isAuditEnabled(){return auditEnabled;} public void setAuditEnabled(boolean v){auditEnabled=v;}
    public boolean isBackupEnabled(){return backupEnabled;} public void setBackupEnabled(boolean v){backupEnabled=v;}
    public Integer getBackupRetentionDays(){return backupRetentionDays;} public void setBackupRetentionDays(Integer v){backupRetentionDays=v;}
    public Integer getPasswordMinLength(){return passwordMinLength;} public void setPasswordMinLength(Integer v){passwordMinLength=v;}
    public String getDefaultRole(){return defaultRole;} public void setDefaultRole(String v){defaultRole=v;}
}
