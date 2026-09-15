package com.example.permission_management_service.kafka;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class PermissionEventPublisher {

    private final KafkaTemplate<String, PermissionEvent> kafkaTemplate;

    public PermissionEventPublisher(
            KafkaTemplate<String, PermissionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(
            String eventType,
            String entityType,
            String entityId,
            Map<String, Object> data) {

        PermissionEvent event = new PermissionEvent(
                UUID.randomUUID().toString(),
                eventType,
                entityType,
                entityId,
                LocalDateTime.now(),
                data
        );

        System.out.println();
        System.out.println("==========================================");
        System.out.println("===== KAFKA EVENT PUBLISHING =====");
        System.out.println("==========================================");
        System.out.println("Topic     : " + KafkaTopics.PERMISSION_EVENTS);
        System.out.println("Event ID  : " + event.getEventId());
        System.out.println("Event Type: " + event.getEventType());
        System.out.println("Entity    : " + event.getEntityType());
        System.out.println("Entity ID : " + event.getEntityId());

        kafkaTemplate
                .send(
                        KafkaTopics.PERMISSION_EVENTS,
                        entityId,
                        event
                )
                .whenComplete((result, exception) -> {

                    if (exception != null) {

                        System.out.println();
                        System.out.println("==========================================");
                        System.out.println("===== KAFKA EVENT PUBLISH FAILED =====");
                        System.out.println("==========================================");

                        exception.printStackTrace();

                    } else {

                        System.out.println();
                        System.out.println("==========================================");
                        System.out.println("===== KAFKA EVENT PUBLISHED =====");
                        System.out.println("==========================================");

                        System.out.println(
                                "Topic     : "
                                        + result.getRecordMetadata().topic()
                        );

                        System.out.println(
                                "Partition : "
                                        + result.getRecordMetadata().partition()
                        );

                        System.out.println(
                                "Offset    : "
                                        + result.getRecordMetadata().offset()
                        );

                        System.out.println(
                                "Event ID  : "
                                        + event.getEventId()
                        );
                    }
                });
    }
}