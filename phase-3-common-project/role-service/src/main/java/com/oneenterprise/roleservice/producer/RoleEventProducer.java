package com.oneenterprise.roleservice.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.oneenterprise.roleservice.dto.RoleEventDto;

@Component
public class RoleEventProducer {

    private final KafkaTemplate<String, RoleEventDto> kafkaTemplate;
    private final String topicName;

    public RoleEventProducer(KafkaTemplate<String, RoleEventDto> kafkaTemplate,
                             @Value("${app.kafka.topics.role-events:role-events-topic}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void publishEvent(String eventType, Long roleId, String roleName, String tenantId) {
        RoleEventDto event = new RoleEventDto(
                eventType,
                roleId,
                roleName,
                tenantId,
                java.time.LocalDateTime.now()
        );
        kafkaTemplate.send(topicName, tenantId, event);
    }
}