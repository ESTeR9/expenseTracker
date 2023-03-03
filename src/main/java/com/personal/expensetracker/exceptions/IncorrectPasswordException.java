package com.personal.expensetracker.exceptions;

public class IncorrectPasswordException extends Exception{
    public IncorrectPasswordException() {
        super("Incorrect password.");
    }
}
