package com.oneenterprise.tenant.dto;

import com.oneenterprise.tenant.entity.IsolationMode;
import com.oneenterprise.tenant.entity.ProvisioningStatus;
import com.oneenterprise.tenant.entity.ProvisioningStrategy;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ProvisioningResponse {
    private UUID id, tenantId; private ProvisioningStrategy strategy; private IsolationMode isolationMode; private ProvisioningStatus status;
    private String schemaName, databaseName, failureReason; private OffsetDateTime provisionedAt;
    public UUID getId(){return id;} public void setId(UUID v){id=v;} public UUID getTenantId(){return tenantId;} public void setTenantId(UUID v){tenantId=v;}
    public ProvisioningStrategy getStrategy(){return strategy;} public void setStrategy(ProvisioningStrategy v){strategy=v;} public IsolationMode getIsolationMode(){return isolationMode;} public void setIsolationMode(IsolationMode v){isolationMode=v;}
    public ProvisioningStatus getStatus(){return status;} public void setStatus(ProvisioningStatus v){status=v;} public String getSchemaName(){return schemaName;} public void setSchemaName(String v){schemaName=v;} public String getDatabaseName(){return databaseName;} public void setDatabaseName(String v){databaseName=v;}
    public String getFailureReason(){return failureReason;} public void setFailureReason(String v){failureReason=v;} public OffsetDateTime getProvisionedAt(){return provisionedAt;} public void setProvisionedAt(OffsetDateTime v){provisionedAt=v;}
}
