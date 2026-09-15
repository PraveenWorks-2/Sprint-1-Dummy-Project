package com.example.permission_management_service.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // =========================================================
    // TOPIC
    // =========================================================

    @Bean
    public NewTopic permissionEventsTopic() {
        return new NewTopic(
                KafkaTopics.PERMISSION_EVENTS,
                3,
                (short) 1
        );
    }

    // =========================================================
    // PRODUCER
    // =========================================================

    @Bean
    public ProducerFactory<String, PermissionEvent> producerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        props.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        props.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class
        );

        props.put(
                ProducerConfig.ACKS_CONFIG,
                "all"
        );

        props.put(
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
                true
        );

        return new DefaultKafkaProducerFactory<>(props);
    }

    // =========================================================
    // KAFKA TEMPLATE
    // =========================================================

    @Bean
    public KafkaTemplate<String, PermissionEvent> kafkaTemplate(
            ProducerFactory<String, PermissionEvent> producerFactory) {

        return new KafkaTemplate<>(producerFactory);
    }

    // =========================================================
    // CONSUMER
    // =========================================================

    @Bean
    public ConsumerFactory<String, PermissionEvent> consumerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "permission-events-audit-group"
        );

        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class
        );

        JsonDeserializer<PermissionEvent> deserializer =
                new JsonDeserializer<>(PermissionEvent.class);

        deserializer.addTrustedPackages(
                "com.example.permission_management_service.kafka"
        );

        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    // =========================================================
    // LISTENER CONTAINER
    // =========================================================

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PermissionEvent>
    kafkaListenerContainerFactory(
            ConsumerFactory<String, PermissionEvent> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, PermissionEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}