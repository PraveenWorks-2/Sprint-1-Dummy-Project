package com.oneenterprise.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Value("${app.kafka.topic.notification-events}")
    private String notificationEventsTopic;

    public void publishNotificationSentEvent(NotificationEvent event) {
        log.info("Publishing notification event to topic '{}': {}", notificationEventsTopic, event);
        kafkaTemplate.send(notificationEventsTopic, String.valueOf(event.getUserId()), event);
    }
}
