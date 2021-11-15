package com.example.demo.config;

import com.example.demo.model.Alert;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerConfiguration {

    @Value("${kafka.alert.group.id}")
    private String ALERT_GROUP_ID;

    @Value("${kafka.message.group.id}")
    private String MESSAGE_GROUP_ID;

    @Value("${bootstrap.server}")
    private String BOOTSTRAP_ADDRESS;

    @Bean
    public ConsumerFactory<String, Alert> alertConsumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, ALERT_GROUP_ID);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Alert.class));
    }

    @Bean(name = "alertListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Alert> alertListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Alert> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(alertConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> messageConsumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, MESSAGE_GROUP_ID);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean(name = "messageListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> messageListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(messageConsumerFactory());
        return factory;
    }

}