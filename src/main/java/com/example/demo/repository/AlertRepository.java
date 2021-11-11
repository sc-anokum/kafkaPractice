package com.example.demo.repository;

import com.example.demo.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlertRepository extends MongoRepository<Alert, String> {
}