package com.personal.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class ExpenseReportRequestBody implements Serializable {
    @NotBlank
    private final String username;

    @NotBlank
    private final String month;

    public ExpenseReportRequestBody(@JsonProperty("username") String username, @JsonProperty("month") String month) {
        this.username = username;
        this.month = month;
    }
}
