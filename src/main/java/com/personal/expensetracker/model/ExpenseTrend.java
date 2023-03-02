package com.personal.expensetracker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExpenseTrend {
    private final String type;
    private Map<String, Double> percentageChanges;

    public ExpenseTrend(String type) {
        this.type = type;
    }
}
