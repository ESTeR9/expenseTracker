package com.personal.expensetracker.exceptions;

public class UserNotRegisteredException  extends Exception{
    //TODO: Choose appropriate HttpStatus Code and Return Error message to response
    public UserNotRegisteredException() { super("User Not Registered."); }
}
