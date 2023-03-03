package com.personal.expensetracker.service;

import com.personal.expensetracker.repository.UserRepository;
import com.personal.expensetracker.exceptions.IncorrectPasswordException;
import com.personal.expensetracker.exceptions.UserNameAlreadyExistsException;
import com.personal.expensetracker.exceptions.UserNotRegisteredException;
import com.personal.expensetracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private final UserRepository userDao;

    @Autowired
    public UserService(@Qualifier("userRepositoryImpl") UserRepository userDao){
        this.userDao =userDao;
    }

    public String addUser(User user) throws ExecutionException, InterruptedException, UserNameAlreadyExistsException {
        return userDao.insertUser(user);
    }

    public Boolean login(User user) throws IncorrectPasswordException, UserNotRegisteredException, ExecutionException, InterruptedException {
        return userDao.login(user);
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
