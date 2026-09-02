package com.oneenterprise.securitysession.kafka;

import java.time.LocalDateTime;

public class SecurityActivityEvent {

    private String eventId;
    private String eventType;
    private Long userId;
    private Long sessionId;
    private String deviceId;
    private String ipAddress;
    private Boolean success;
    private String details;
    private LocalDateTime timestamp;

    public SecurityActivityEvent() {
    }

    public SecurityActivityEvent(
            String eventId,
            String eventType,
            Long userId,
            Long sessionId,
            String deviceId,
            String ipAddress,
            Boolean success,
            String details,
            LocalDateTime timestamp) {

        this.eventId = eventId;
        this.eventType = eventType;
        this.userId = userId;
        this.sessionId = sessionId;
        this.deviceId = deviceId;
        this.ipAddress = ipAddress;
        this.success = success;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}