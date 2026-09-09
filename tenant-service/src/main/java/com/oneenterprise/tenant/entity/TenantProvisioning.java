package com.oneenterprise.tenant.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenant_provisioning", indexes = {
        @Index(name = "idx_provisioning_tenant_id", columnList = "tenant_id", unique = true)
})
public class TenantProvisioning {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, unique = true)
    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProvisioningStrategy strategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "isolation_mode", nullable = false, length = 30)
    private IsolationMode isolationMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProvisioningStatus status;

    @Column(name = "schema_name", length = 63)
    private String schemaName;

    @Column(name = "database_name", length = 100)
    private String databaseName;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Column(name = "provisioned_at")
    private OffsetDateTime provisionedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() { updatedAt = OffsetDateTime.now(); }

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public ProvisioningStrategy getStrategy() { return strategy; }
    public void setStrategy(ProvisioningStrategy strategy) { this.strategy = strategy; }
    public IsolationMode getIsolationMode() { return isolationMode; }
    public void setIsolationMode(IsolationMode isolationMode) { this.isolationMode = isolationMode; }
    public ProvisioningStatus getStatus() { return status; }
    public void setStatus(ProvisioningStatus status) { this.status = status; }
    public String getSchemaName() { return schemaName; }
    public void setSchemaName(String schemaName) { this.schemaName = schemaName; }
    public String getDatabaseName() { return databaseName; }
    public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    public OffsetDateTime getProvisionedAt() { return provisionedAt; }
    public void setProvisionedAt(OffsetDateTime provisionedAt) { this.provisionedAt = provisionedAt; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
