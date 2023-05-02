package com.example.demo.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(long id){
        super("No Course found with id: " + id);
    }
}
