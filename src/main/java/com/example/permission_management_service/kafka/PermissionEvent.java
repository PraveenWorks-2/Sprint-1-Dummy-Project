package com.example.permission_management_service.kafka;

import java.time.LocalDateTime;
import java.util.Map;

public class PermissionEvent {
    private String eventId;
    private String eventType;
    private String entityType;
    private String entityId;
    private LocalDateTime timestamp;
    private Map<String, Object> data;

    public PermissionEvent() {}

    public PermissionEvent(String eventId, String eventType, String entityType,
                           String entityId, LocalDateTime timestamp, Map<String, Object> data) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.timestamp = timestamp;
        this.data = data;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
}