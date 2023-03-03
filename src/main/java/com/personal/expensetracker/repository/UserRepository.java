package com.personal.expensetracker.repository;

import com.personal.expensetracker.exceptions.IncorrectPasswordException;
import com.personal.expensetracker.exceptions.UserNameAlreadyExistsException;
import com.personal.expensetracker.exceptions.UserNotRegisteredException;
import com.personal.expensetracker.model.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface UserRepository {
    String insertUser(User user) throws ExecutionException, InterruptedException, UserNameAlreadyExistsException;

    List<User> selectAllUsers();

    Optional<User> selectUserById(String id);

    int deleteUserById(String id);

    int updateUserById(String id,User user);

    Boolean login(User user) throws UserNotRegisteredException, ExecutionException, InterruptedException, IncorrectPasswordException;
}
