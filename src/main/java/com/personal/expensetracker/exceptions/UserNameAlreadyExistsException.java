package com.personal.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNameAlreadyExistsException extends Exception{
    //TODO: Choose appropriate HttpStatus Code and Return Error message to response
    public UserNameAlreadyExistsException(){
        super("Username already exists.");
    }
}
