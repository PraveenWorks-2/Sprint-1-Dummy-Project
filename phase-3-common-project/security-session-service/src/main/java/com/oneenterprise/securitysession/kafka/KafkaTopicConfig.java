package com.oneenterprise.securitysession.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic securityActivityTopic(
            @Value("${app.kafka.topic.security-activity}") String topicName) {

        return new NewTopic(topicName, 1, (short) 1);
    }
}