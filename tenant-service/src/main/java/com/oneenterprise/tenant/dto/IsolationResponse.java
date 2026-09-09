package com.oneenterprise.tenant.dto;

import com.oneenterprise.tenant.entity.IsolationMode;
import java.util.UUID;

public class IsolationResponse {
    private UUID tenantId; private IsolationMode isolationMode; private String databaseName, schemaName, enforcement;
    public UUID getTenantId(){return tenantId;} public void setTenantId(UUID v){tenantId=v;} public IsolationMode getIsolationMode(){return isolationMode;} public void setIsolationMode(IsolationMode v){isolationMode=v;}
    public String getDatabaseName(){return databaseName;} public void setDatabaseName(String v){databaseName=v;} public String getSchemaName(){return schemaName;} public void setSchemaName(String v){schemaName=v;} public String getEnforcement(){return enforcement;} public void setEnforcement(String v){enforcement=v;}
}
