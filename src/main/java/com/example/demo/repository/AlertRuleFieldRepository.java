package com.example.demo.repository;

import com.example.demo.model.AlertRuleField;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlertRuleFieldRepository extends MongoRepository<AlertRuleField,String> {
}