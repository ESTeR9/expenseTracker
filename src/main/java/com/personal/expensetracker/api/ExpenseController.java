package com.personal.expensetracker.api;

import com.personal.expensetracker.config.AppConfiguration;
import com.personal.expensetracker.model.Expense;
import com.personal.expensetracker.model.ExpenseReport;
import com.personal.expensetracker.model.User;
import com.personal.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RequestMapping("api/v1/expense")
@RestController
public class ExpenseController {
    private final ExpenseService expenseService;
    private AppConfiguration appConfiguration;

    @Autowired
    public ExpenseController(ExpenseService expenseService, AppConfiguration appConfiguration) {
        this.expenseService = expenseService;
        this.appConfiguration = appConfiguration;
    }

    @PostMapping
    public void addExpense(@RequestBody Expense expense) throws ExecutionException, InterruptedException
    {
        this.expenseService.addExpense(expense);
    }

    @GetMapping(path = "expenseReport")
    public ExpenseReport selectExpenseReportByUsernameAndMonth(@RequestParam(name = "username") String username,
                                                               @RequestParam(name = "password") String password,
                                                               @RequestParam(name = "month") String month)
                                                    throws ExecutionException, InterruptedException {
        return this.expenseService.selectExpenseReportByUsernameAndMonth(new User(password,username), month);
    }

//    @GetMapping(path = "expenseTrends")
//    public ExpenseSummary selectUserExpenseSummary(@RequestParam(name = "username") String username,
//                                                   @RequestParam(name = "password") String password){
//        return this.expenseService.selectUserExpenseSummar
//    }
}
