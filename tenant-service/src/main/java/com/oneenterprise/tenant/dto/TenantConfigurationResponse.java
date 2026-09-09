package com.oneenterprise.tenant.dto;

import java.util.UUID;

public class TenantConfigurationResponse {
    private UUID id, tenantId; private String currency, dateFormat, defaultRole;
    private boolean emailEnabled, notificationsEnabled, selfServiceEnabled, auditEnabled, backupEnabled;
    private Integer maxUsers, dataRetentionDays, maxStorageMb, sessionTimeoutMinutes, backupRetentionDays, passwordMinLength;
    public UUID getId(){return id;} public void setId(UUID v){id=v;} public UUID getTenantId(){return tenantId;} public void setTenantId(UUID v){tenantId=v;}
    public String getCurrency(){return currency;} public void setCurrency(String v){currency=v;} public String getDateFormat(){return dateFormat;} public void setDateFormat(String v){dateFormat=v;}
    public boolean isEmailEnabled(){return emailEnabled;} public void setEmailEnabled(boolean v){emailEnabled=v;} public boolean isNotificationsEnabled(){return notificationsEnabled;} public void setNotificationsEnabled(boolean v){notificationsEnabled=v;}
    public boolean isSelfServiceEnabled(){return selfServiceEnabled;} public void setSelfServiceEnabled(boolean v){selfServiceEnabled=v;} public boolean isAuditEnabled(){return auditEnabled;} public void setAuditEnabled(boolean v){auditEnabled=v;} public boolean isBackupEnabled(){return backupEnabled;} public void setBackupEnabled(boolean v){backupEnabled=v;}
    public Integer getMaxUsers(){return maxUsers;} public void setMaxUsers(Integer v){maxUsers=v;} public Integer getDataRetentionDays(){return dataRetentionDays;} public void setDataRetentionDays(Integer v){dataRetentionDays=v;} public Integer getMaxStorageMb(){return maxStorageMb;} public void setMaxStorageMb(Integer v){maxStorageMb=v;}
    public Integer getSessionTimeoutMinutes(){return sessionTimeoutMinutes;} public void setSessionTimeoutMinutes(Integer v){sessionTimeoutMinutes=v;} public Integer getBackupRetentionDays(){return backupRetentionDays;} public void setBackupRetentionDays(Integer v){backupRetentionDays=v;} public Integer getPasswordMinLength(){return passwordMinLength;} public void setPasswordMinLength(Integer v){passwordMinLength=v;}
    public String getDefaultRole(){return defaultRole;} public void setDefaultRole(String v){defaultRole=v;}
}
