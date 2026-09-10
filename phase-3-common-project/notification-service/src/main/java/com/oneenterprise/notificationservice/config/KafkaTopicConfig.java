package com.oneenterprise.notificationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topic.role-assigned}")
    private String roleAssignedTopic;

    @Value("${app.kafka.topic.permission-changed}")
    private String permissionChangedTopic;

    @Value("${app.kafka.topic.security-activity}")
    private String securityActivityTopic;

    @Value("${app.kafka.topic.notification-events}")
    private String notificationEventsTopic;

    @Bean
    public NewTopic roleAssignedEventsTopic() {
        return TopicBuilder.name(roleAssignedTopic).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic permissionChangedEventsTopic() {
        return TopicBuilder.name(permissionChangedTopic).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic securityActivityEventsTopic() {
        return TopicBuilder.name(securityActivityTopic).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic notificationEventsTopic() {
        return TopicBuilder.name(notificationEventsTopic).partitions(3).replicas(1).build();
    }
}
