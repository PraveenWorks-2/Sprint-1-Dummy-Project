package com.oneenterprise.userrole.kafka;

public class UserRoleEvent {

    private Long userId;
    private Long roleId;
    private String eventType;

    public UserRoleEvent() {
    }

    public UserRoleEvent(Long userId, Long roleId, String eventType) {
        this.userId = userId;
        this.roleId = roleId;
        this.eventType = eventType;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "UserRoleEvent{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}