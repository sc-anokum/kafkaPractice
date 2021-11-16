package com.example.demo.config;

import com.example.demo.model.Alert;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {

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
    public ProducerFactory<String, Alert> alertProducerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, KEYSTORE_LOCATION);
        config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, TRUSTSTORE_LOCATION);
        config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, TRUSTORE_PASSWORD);
        config.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Alert> kafkaTemplateAlertProducer(){
        return new KafkaTemplate<>(alertProducerFactory());

    }

    @Bean
    public ProducerFactory<String, String> messageProducerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, KEYSTORE_LOCATION);
        config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, TRUSTSTORE_LOCATION);
        config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, TRUSTORE_PASSWORD);
        config.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, KEYSTORE_PASSWORD);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplateMessageProducer(){
        return new KafkaTemplate<>(messageProducerFactory());

    }

}
