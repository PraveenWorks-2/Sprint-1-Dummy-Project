package com.oneenterprise.tenant.dto;

import com.oneenterprise.tenant.entity.BackupStatus;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BackupResponse {
    private UUID id, tenantId; private BackupStatus status; private String backupType, location; private OffsetDateTime createdAt, retentionUntil;
    public UUID getId(){return id;} public void setId(UUID v){id=v;} public UUID getTenantId(){return tenantId;} public void setTenantId(UUID v){tenantId=v;} public BackupStatus getStatus(){return status;} public void setStatus(BackupStatus v){status=v;}
    public String getBackupType(){return backupType;} public void setBackupType(String v){backupType=v;} public String getLocation(){return location;} public void setLocation(String v){location=v;} public OffsetDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(OffsetDateTime v){createdAt=v;} public OffsetDateTime getRetentionUntil(){return retentionUntil;} public void setRetentionUntil(OffsetDateTime v){retentionUntil=v;}
}
