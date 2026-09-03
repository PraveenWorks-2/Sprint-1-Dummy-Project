package com.oneenterprise.securitysession.kafka;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SecurityEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topic.security-activity}")
    private String securityActivityTopic;

    public SecurityEventProducer(
            KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(
            String eventType,
            Long userId,
            Long sessionId,
            String deviceId,
            String ipAddress,
            Boolean success,
            String details) {

        String eventId = UUID.randomUUID().toString();

        String payload = """
                {
                  "eventId": "%s",
                  "eventType": "%s",
                  "userId": %s,
                  "sessionId": %s,
                  "deviceId": %s,
                  "ipAddress": %s,
                  "success": %s,
                  "details": "%s",
                  "timestamp": "%s"
                }
                """.formatted(
                escape(eventId),
                escape(eventType),
                userId == null ? "null" : userId.toString(),
                sessionId == null ? "null" : sessionId.toString(),
                jsonString(deviceId),
                jsonString(ipAddress),
                success == null ? "null" : success.toString(),
                escape(details == null ? "" : details),
                LocalDateTime.now()
        );

        String key = userId != null
                ? userId.toString()
                : eventId;

        kafkaTemplate.send(
                securityActivityTopic,
                key,
                payload
        ).whenComplete((result, exception) -> {

            if (exception != null) {

                System.err.println(
                        "Kafka event publish failed. "
                        + "eventType=" + eventType
                        + ", eventId=" + eventId
                        + ", error=" + exception.getMessage()
                );

            } else {

                System.out.println(
                        "Kafka event published successfully. "
                        + "eventType=" + eventType
                        + ", eventId=" + eventId
                        + ", topic=" + securityActivityTopic
                        + ", partition="
                        + result.getRecordMetadata().partition()
                        + ", offset="
                        + result.getRecordMetadata().offset()
                );
            }
        });
    }

    private String jsonString(String value) {

        if (value == null) {
            return "null";
        }

        return "\"" + escape(value) + "\"";
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