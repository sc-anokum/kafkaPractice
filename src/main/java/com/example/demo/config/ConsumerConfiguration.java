package com.example.demo.config;

import com.example.demo.model.Alert;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
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

    @Value("${ssl.keystore.location}")
    private String KEYSTORE_LOCATION;

    @Value("${ssl.keystore.password}")
    private String KEYSTORE_PASSWORD;

    @Value("${ssl.truststore.location}")
    private String TRUSTSTORE_LOCATION;

    @Value("${ssl.truststore.password}")
    private String TRUSTORE_PASSWORD;

    @Bean
    public ConsumerFactory<String, Alert> alertConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, ALERT_GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        config.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, KEYSTORE_LOCATION);
        config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, TRUSTSTORE_LOCATION);
        config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, TRUSTORE_PASSWORD);
        config.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        System.out.println(config);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Alert.class));
    }

    @Bean(name = "alertListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Alert> alertListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Alert> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(alertConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> messageConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, MESSAGE_GROUP_ID);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, KEYSTORE_LOCATION);
        config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, TRUSTSTORE_LOCATION);
        config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, TRUSTORE_PASSWORD);
        config.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean(name = "messageListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> messageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(messageConsumerFactory());
        return factory;
    }

}