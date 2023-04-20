package com.example.demo.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(int id){
        super("User id not found: " + id);
    }
}
