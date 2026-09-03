package com.oneenterprise.securitysession.kafka;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
 
@Component
public class SecurityEventProducer {
 
    private static final String SOURCE_SERVICE = "security-session-service";
 
    private final KafkaTemplate<String, String> kafkaTemplate;
 
    @Value("${app.kafka.topic.security-activity}")
    private String securityActivityTopic;
 
    public SecurityEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
 
    /**
     * @param action     must match AuditAction enum: CREATE, UPDATE, DELETE,
     *                   LOGIN, LOGOUT, ROLE_ASSIGNED, ROLE_REMOVED,
     *                   PERMISSION_GRANTED, PERMISSION_REVOKED, ACCESS_DENIED, OTHER
     * @param tenantId   tenant context for this activity
     * @param userId     the acting user's id
     * @param entityName e.g. "UserSession", "LoginHistory"
     * @param entityId   id of the affected entity, as a string
     * @param description human-readable summary of what happened
     */
    public void publish(
            String action,
            Long tenantId,
            Long userId,
            String entityName,
            String entityId,
            String description) {
 
        String payload = """
                {
                  "userId": %s,
                  "tenantId": %s,
                  "action": "%s",
                  "module": "SECURITY",
                  "entityName": "%s",
                  "entityId": "%s",
                  "description": "%s",
                  "sourceService": "%s"
                }
                """.formatted(
                userId == null ? "null" : userId.toString(),
                tenantId == null ? "null" : tenantId.toString(),
                escape(action),
                escape(entityName),
                escape(entityId),
                escape(description == null ? "" : description),
                SOURCE_SERVICE
        );
 
        String key = userId != null ? userId.toString() : SOURCE_SERVICE;
 
        kafkaTemplate.send(securityActivityTopic, key, payload)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        System.err.println(
                                "Kafka event publish failed. "
                                + "action=" + action
                                + ", userId=" + userId
                                + ", error=" + exception.getMessage()
                        );
                    } else {
                        System.out.println(
                                "Kafka event published successfully. "
                                + "action=" + action
                                + ", userId=" + userId
                                + ", topic=" + securityActivityTopic
                                + ", partition=" + result.getRecordMetadata().partition()
                                + ", offset=" + result.getRecordMetadata().offset()
                        );
                    }
                });
    }
 
    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}