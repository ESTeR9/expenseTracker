package com.personal.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//Can get rid of user and month fields

@Getter
@Setter
public class Expense implements Serializable {
    private final User user;

    private final String month;

    private final String expenseType;

    private final Double expenseAmount;

    public Expense(@JsonProperty("user") User user,@JsonProperty("month") String month, @JsonProperty("expenseType") String expenseType,
                   @JsonProperty("expenseAmount") Double expenseAmount)
    {
        this.user=user;
        this.month = month;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
    }
}
