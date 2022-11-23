package com.personal.expensetracker.dao;

import com.personal.expensetracker.model.User;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ExpenseDao {
    String insertExpense(User user, String month, String expenseType, Double expenseAmount) throws ExecutionException,
            InterruptedException;

    Map<String, Double> selectExpenseReportByUsernameAndMonth(User user, String month) throws ExecutionException,
            InterruptedException;
}
