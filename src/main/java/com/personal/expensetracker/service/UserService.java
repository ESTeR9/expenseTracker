package com.personal.expensetracker.service;

import com.personal.expensetracker.dao.UserDao;
import com.personal.expensetracker.exceptions.UserNameAlreadyExistsException;
import com.personal.expensetracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("userDai") UserDao userDao){
        this.userDao =userDao;
    }

    public String addUser(User user) throws ExecutionException, InterruptedException, UserNameAlreadyExistsException {
        return userDao.insertUser(user);
    }

    public List<User>  getAllUsers(){
        return userDao.selectAllUsers();
    }

    public Optional<User> getUserById(String id){
        return userDao.selectUserById(id);
    }

    public int deleteUserById(String id){
        return userDao.deleteUserById(id);
    }

    public int updateUser(String id, User newUser){
        return userDao.updateUserById(id,newUser);
    }
}
