package com.personal.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//Can get rid of user and month fields

@Getter
@Setter
public class Expense implements Serializable {
    private final String username;

    private final String month;

    private final String expenseType;

    private final Double expenseAmount;

    public Expense(@JsonProperty("username") String username,@JsonProperty("month") String month, @JsonProperty("expenseType") String expenseType,
                   @JsonProperty("expenseAmount") Double expenseAmount)
    {
        this.username=username;
        this.month = month;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
    }
}
