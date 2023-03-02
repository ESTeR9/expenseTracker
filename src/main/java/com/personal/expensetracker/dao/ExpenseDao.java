package com.personal.expensetracker.dao;

import com.personal.expensetracker.model.User;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ExpenseDao {
    String insertExpense(String username, String month, String expenseType, Double expenseAmount) throws ExecutionException,
            InterruptedException;

    Map<String, Double> selectExpenseReportByUsernameAndMonth(String username, String month);
}
