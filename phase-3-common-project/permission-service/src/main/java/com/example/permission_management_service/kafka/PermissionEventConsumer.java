package com.example.permission_management_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PermissionEventConsumer {

    @PostConstruct
    public void init() {
        System.out.println("===== KAFKA CONSUMER STARTED =====");
    }

    @KafkaListener(
            topics = KafkaTopics.PERMISSION_EVENTS,
            groupId = "permission-events-audit-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PermissionEvent event) {

        System.out.printf(
                "KAFKA EVENT RECEIVED | type=%s | entity=%s | id=%s | eventId=%s%n",
                event.getEventType(),
                event.getEntityType(),
                event.getEntityId(),
                event.getEventId()
        );
    }
}