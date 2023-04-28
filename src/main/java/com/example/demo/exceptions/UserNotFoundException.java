package com.example.demo.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(int id){
        super("User id not found: " + id);

    }

    public UserNotFoundException(String name){
        super("User not found with name: " + name);
    }
}
