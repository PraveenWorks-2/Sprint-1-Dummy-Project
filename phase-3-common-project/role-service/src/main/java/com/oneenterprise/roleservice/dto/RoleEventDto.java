package com.oneenterprise.roleservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RoleEventDto implements Serializable {
    private String eventType;
    private Long roleId;
    private String roleName;
    private String tenantId;
    private LocalDateTime timestamp;

    public RoleEventDto() {
    }

    public RoleEventDto(String eventType, Long roleId, String roleName, String tenantId, LocalDateTime timestamp) {
        this.eventType = eventType;
        this.roleId = roleId;
        this.roleName = roleName;
        this.tenantId = tenantId;
        this.timestamp = timestamp;
    }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}