package com.example.demo.service;

import com.example.demo.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Value("${kafka.alert.topic}")
    private String ALERT_TOPIC;

    @Value("${kafka.message.topic}")
    private String MESSAGE_TOPIC;

    @Autowired
    KafkaTemplate<String, Alert> alertKafkaTemplate;

    @Autowired
    KafkaTemplate<String, String> messageKafkaTemplate;

    public void publishAlert(Alert msg){
        alertKafkaTemplate.send(ALERT_TOPIC, msg);
    }

    public void publishMessage(String msg){
        messageKafkaTemplate.send(MESSAGE_TOPIC, msg);
    }

    @KafkaListener(topics = "${kafka.alert.topic}", groupId = "${kafka.alert.group.id}", containerFactory = "alertListenerFactory")
    public void consumeAlert(Alert alert){
        System.out.println(alert);
    }

    @KafkaListener(topics = "${kafka.message.topic}", groupId = "${kafka.message.group.id}", containerFactory = "messageListenerFactory")
    public void consumeMessage(String message){
        System.out.println(message);
    }
}
