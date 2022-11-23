package com.personal.expensetracker.dao;

import com.personal.expensetracker.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface UserDao {
    String insertUser(String id, User user) throws ExecutionException, InterruptedException;

    default String insertUser(User user) throws ExecutionException, InterruptedException {
        String id = UUID.randomUUID().toString();
        return insertUser(id,user);
    }

    List<User> selectAllUsers();

    Optional<User> selectUserById(String id);

    int deleteUserById(String id);

    int updateUserById(String id,User user);
}
