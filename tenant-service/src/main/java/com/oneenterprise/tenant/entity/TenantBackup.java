package com.oneenterprise.tenant.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenant_backups", indexes = {
        @Index(name = "idx_backup_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_backup_created_at", columnList = "created_at")
})
public class TenantBackup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BackupStatus status;

    @Column(name = "backup_type", nullable = false, length = 30)
    private String backupType;

    @Column(name = "location", nullable = false, length = 300)
    private String location;

    @Column(name = "retention_until")
    private OffsetDateTime retentionUntil;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() { createdAt = OffsetDateTime.now(); }

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public BackupStatus getStatus() { return status; }
    public void setStatus(BackupStatus status) { this.status = status; }
    public String getBackupType() { return backupType; }
    public void setBackupType(String backupType) { this.backupType = backupType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public OffsetDateTime getRetentionUntil() { return retentionUntil; }
    public void setRetentionUntil(OffsetDateTime retentionUntil) { this.retentionUntil = retentionUntil; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
