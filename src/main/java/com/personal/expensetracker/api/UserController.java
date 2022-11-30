package com.personal.expensetracker.api;

import com.personal.expensetracker.model.User;
import com.personal.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.isNull;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void addUser(@Valid @NotNull @RequestBody User user) throws ExecutionException, InterruptedException {
        userService.addUser(user);
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false, name = "id") String id){
        if(isNull(id)){
            return userService.getAllUsers();
        }
        else{
            return Collections.singletonList(userService.getUserById(id)
                    .orElse(null));
        }
    }

    @DeleteMapping(path = "{id}")
    public void deleteUserById(@PathVariable("id") String id){
        userService.deleteUserById(id);
    }

    @PutMapping(path="{id}")
    public void updateUserById(@PathVariable("id") String id,@Valid @NotNull @RequestBody User newUser){
        userService.updateUser(id,newUser);
    }
}
