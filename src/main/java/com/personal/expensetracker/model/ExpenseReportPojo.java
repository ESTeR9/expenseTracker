package com.personal.expensetracker.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ExpenseReportPojo implements Serializable {
    private final Map<String, Map<String, Double>> reports = new HashMap<>();

    public ExpenseReportPojo(String month){
        Map<String, Double> expense = new HashMap<>();
        expense.put("total",0.0);
        this.reports.put(month,expense);
    }
}
