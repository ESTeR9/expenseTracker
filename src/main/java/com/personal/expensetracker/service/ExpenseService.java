package com.personal.expensetracker.service;

import com.personal.expensetracker.dao.ExpenseDao;
import com.personal.expensetracker.model.Expense;
import com.personal.expensetracker.model.ExpenseReport;
import com.personal.expensetracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;

    @Autowired
    public ExpenseService(@Qualifier("expenseReportDai") ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public String addExpense(Expense expense) throws ExecutionException, InterruptedException {
        return this.expenseDao.insertExpense(expense.getUser(),expense.getMonth(), expense.getExpenseType(),
                expense.getExpenseAmount());
    }

    public ExpenseReport selectExpenseReportByUsernameAndMonth(User user, String month) throws ExecutionException, InterruptedException {
        Map<String, Double> expenseReportMap =  this.expenseDao.selectExpenseReportByUsernameAndMonth(user,month);
        ExpenseReport expenseReport = new ExpenseReport(user.getName(), month);
        expenseReport.setExpenses(expenseReportMap);
        return expenseReport;
    }
}
