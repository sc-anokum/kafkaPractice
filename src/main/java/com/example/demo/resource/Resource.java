package com.example.demo.resource;

import com.example.demo.model.Alert;
import com.example.demo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
public class Resource {

    @Autowired
    KafkaService service;

    @PostMapping("/publishAlert")
    public void publishAlert(@RequestBody Alert alert){
        service.publishAlert(alert);
    }

    @PostMapping("/publishMessage")
    public void publishMessage(@RequestBody String alert){
        service.publishMessage(alert);
    }
}
