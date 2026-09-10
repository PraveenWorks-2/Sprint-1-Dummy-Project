package com.oneenterprise.userrole.dto;

import java.time.LocalDateTime;

public class UserRoleResponse {

    private Long id;
    private Long userId;
    private Long roleId;
    private LocalDateTime assignedAt;
    private String status;

    public UserRoleResponse() {
    }

    public UserRoleResponse(Long id, Long userId, Long roleId,
                            LocalDateTime assignedAt, String status) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.assignedAt = assignedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}