package com.personal.expensetracker.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ExpenseReport implements Serializable {
    private final String username;
    private final String month;

    @Setter
    private  Map<String,Double> expenses = new HashMap<>();

    public ExpenseReport(String username, String month){
        this.username = username;
        this.month = month;
        this.expenses.put("total", 0.0);
    }

    public void addExpense(String expenseType, Double expenseAmount){
        if(this.expenses.containsKey(expenseType)) {
            this.expenses.put(expenseType, expenseAmount);
        }
        else{
            this.expenses.put(expenseType, this.expenses.get(expenseType) + expenseAmount);
        }
        this.expenses.put("total",this.expenses.get("total")+expenseAmount);
    }

    public void subtractExpense(String expenseType, Double expenseAmount){
        if(this.expenses.containsKey(expenseType)) {
            this.expenses.put(expenseType, 0 - expenseAmount);
        }
        else{
            this.expenses.put(expenseType, this.expenses.get(expenseType) - expenseAmount);
        }
        this.expenses.put("total",this.expenses.get("total")-expenseAmount);
    }

    public Double getExpense(String expenseType){
        if(this.expenses.containsKey(expenseType)){
            return this.expenses.get(expenseType);
        }
        return 0.0;
    }

    public Double getTotal(){
        return this.expenses.get("total");
    }
}
