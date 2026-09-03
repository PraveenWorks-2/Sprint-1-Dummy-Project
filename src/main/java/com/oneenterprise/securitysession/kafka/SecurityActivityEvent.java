package com.oneenterprise.securitysession.kafka;
 
public class SecurityActivityEvent {
 
    private Long userId;
    private Long tenantId;
    private String action;
    private String module;
    private String entityName;
    private String entityId;
    private String description;
    private String sourceService;
 
    public SecurityActivityEvent() {
    }
 
    public SecurityActivityEvent(
            Long userId,
            Long tenantId,
            String action,
            String module,
            String entityName,
            String entityId,
            String description,
            String sourceService) {
 
        this.userId = userId;
        this.tenantId = tenantId;
        this.action = action;
        this.module = module;
        this.entityName = entityName;
        this.entityId = entityId;
        this.description = description;
        this.sourceService = sourceService;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public Long getTenantId() {
        return tenantId;
    }
 
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
 
    public String getAction() {
        return action;
    }
 
    public void setAction(String action) {
        this.action = action;
    }
 
    public String getModule() {
        return module;
    }
 
    public void setModule(String module) {
        this.module = module;
    }
 
    public String getEntityName() {
        return entityName;
    }
 
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
 
    public String getEntityId() {
        return entityId;
    }
 
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public String getSourceService() {
        return sourceService;
    }
 
    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }
}