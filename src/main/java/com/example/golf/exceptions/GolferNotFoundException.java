package com.example.golf.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Golfer")
public class GolferNotFoundException extends RuntimeException{

    public GolferNotFoundException(int id){
        super("User id not found: " + id);

    }

    public GolferNotFoundException(String name){
        super("User not found with name: " + name);
    }
}
