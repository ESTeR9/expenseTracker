package com.personal.expensetracker.dao;

import com.personal.expensetracker.exceptions.UserNameAlreadyExistsException;
import com.personal.expensetracker.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface UserDao {
    String insertUser(User user) throws ExecutionException, InterruptedException, UserNameAlreadyExistsException;

    List<User> selectAllUsers();

    Optional<User> selectUserById(String id);

    int deleteUserById(String id);

    int updateUserById(String id,User user);
}
