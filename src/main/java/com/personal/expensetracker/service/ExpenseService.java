package com.personal.expensetracker.service;

import com.personal.expensetracker.repository.ExpenseDao;
import com.personal.expensetracker.model.Expense;
import com.personal.expensetracker.model.ExpenseReport;
import com.personal.expensetracker.model.ExpenseTrend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.ExecutionException;

import java.time.LocalDate;

import static java.util.Objects.nonNull;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;

    @Autowired
    public ExpenseService(@Qualifier("expenseReportDai") ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public String addExpense(Expense expense) throws ExecutionException, InterruptedException {
        return this.expenseDao.insertExpense(expense.getUsername(),expense.getMonth(), expense.getExpenseType(),
                expense.getExpenseAmount());
    }

    public ExpenseReport selectExpenseReportByUsernameAndMonth(String username, String month) {
        Map<String, Double> expenseReportMap =  this.expenseDao.selectExpenseReportByUsernameAndMonth(username,month);
        ExpenseReport expenseReport = new ExpenseReport(username, month);
        expenseReport.setExpenses(expenseReportMap);
        return expenseReport;
    }

    public ExpenseTrend getMonthlyExpenseTrend(String username) {
        LocalDate today = LocalDate.now();
        String  currentYear = String.valueOf(today.getYear());
        String currentMonth =  today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase();
        String previousMonth = today.minusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase();
        String prevMonthYear = currentMonth.equals("january") ? String.valueOf(today.getYear()-1): currentYear;
        Map<String, Double> currentMonthExpenses = this.expenseDao.selectExpenseReportByUsernameAndMonth(username,currentMonth+currentYear);
        Map<String, Double> previousMonthExpenses = this.expenseDao.selectExpenseReportByUsernameAndMonth(username,previousMonth+prevMonthYear);
        if(nonNull(currentMonthExpenses) && nonNull(previousMonthExpenses)){
            Set<String> previousMonthExpenseTypes = previousMonthExpenses.keySet();
            Map<String, Double> percentageChanges = new HashMap<>();
            for(String key : currentMonthExpenses.keySet()) {
                if (previousMonthExpenses.containsKey(key)) {
                    Double currentExpense = currentMonthExpenses.get(key);
                    Double previousExpense = previousMonthExpenses.get(key);
                    previousMonthExpenseTypes.remove(key);
                    if (previousExpense != 0)
                        percentageChanges.put(key,
                                ((currentExpense - previousExpense) * 100) / previousExpense);
                }
            }
            for(String key : previousMonthExpenseTypes){
                percentageChanges.put(key,-100.0);
            }
            ExpenseTrend expenseTrend = new ExpenseTrend("Monthly");
            expenseTrend.setPercentageChanges(percentageChanges);
            return expenseTrend;
        }
        return null;
    }
}
