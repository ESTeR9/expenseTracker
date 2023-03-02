package com.personal.expensetracker.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.expensetracker.exceptions.UserNameAlreadyExistsException;
import com.personal.expensetracker.model.*;
import com.personal.expensetracker.service.ExpenseService;
import com.personal.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutionException;


@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final ExpenseService expenseService;

    @Autowired
    public UserController(UserService userService, ExpenseService expenseService) {
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @PostMapping("/register")
    public String addUser(@Valid @NotNull @RequestBody User user) throws ExecutionException, InterruptedException, UserNameAlreadyExistsException {
        return userService.addUser(user);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addExpense")
    public void addExpense(@RequestBody Expense expense, Authentication authentication) throws Exception {
        //TODO define custom exception
        if(!authentication.getPrincipal().toString().equals(expense.getUsername()))
            throw new Exception();
        this.expenseService.addExpense(expense);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/expenseReport")
    public ExpenseReport selectExpenseReportByUsernameAndMonth(@Valid @NotNull @RequestBody ExpenseReportRequestBody expenseReportRequestBody, Authentication authentication)
            throws Exception {
        //TODO define custom exception
        if(!authentication.getPrincipal().toString().equals(expenseReportRequestBody.getUsername()))
            throw new Exception();
        return this.expenseService.selectExpenseReportByUsernameAndMonth(expenseReportRequestBody.getUsername(), expenseReportRequestBody.getMonth());
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping(path="/monthlyExpenseTrend")
    public ExpenseTrend getMonthlyExpenseTrend(@Valid @NotNull @RequestBody @JsonProperty String username,Authentication authentication) throws Exception {
        if(!authentication.getPrincipal().toString().equals(username))
            throw new Exception();
        return this.expenseService.getMonthlyExpenseTrend(username);
    }

//    @GetMapping
//    public List<User> getUsers(@RequestParam(required = false, name = "id") String id){
//        if(isNull(id)){
//            return userService.getAllUsers();
//        }
//        else{
//            return Collections.singletonList(userService.getUserById(id)
//                    .orElse(null));
//        }
//    }
//
//    @DeleteMapping(path = "{id}")
//    public void deleteUserById(@PathVariable("id") String id){
//        userService.deleteUserById(id);
//    }
//
//    @PutMapping(path="{id}")
//    public void updateUserById(@PathVariable("id") String id,@Valid @NotNull @RequestBody User newUser){
//        userService.updateUser(id,newUser);
//    }
}
