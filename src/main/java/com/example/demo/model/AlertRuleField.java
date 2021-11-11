package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertRuleField {
    private String ruleID;
    private String ruleName;
    private String ruleType;
    private String status;
    private long consecutiveOccurrence;
    private float confidenceScore;
    private long createdOn;
}
