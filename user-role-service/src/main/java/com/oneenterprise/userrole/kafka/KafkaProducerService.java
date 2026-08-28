package com.oneenterprise.userrole.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "user-role-events";

    private final KafkaTemplate<String, UserRoleEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, UserRoleEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRoleAssignedEvent(Long userId, Long roleId) {

        UserRoleEvent event = new UserRoleEvent(
                userId,
                roleId,
                "ROLE_ASSIGNED"
        );

        kafkaTemplate.send(
                TOPIC,
                String.valueOf(userId),
                event
        );
    }

    public void sendRoleRemovedEvent(Long userId, Long roleId) {

        UserRoleEvent event = new UserRoleEvent(
                userId,
                roleId,
                "ROLE_REMOVED"
        );

        kafkaTemplate.send(
                TOPIC,
                String.valueOf(userId),
                event
        );
    }
}