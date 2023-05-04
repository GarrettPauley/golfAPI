package com.example.demo.controllers;

import com.example.demo.domain.Course;
import com.example.demo.exceptions.CourseNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import org.h2.engine.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService _courseService){
        this.courseService = _courseService;
    }

    // Get all courses.
    @GetMapping("/courses")
    public List<Course> getCourses(){
        return courseService.getCourses();
    }
    // Get one course.
    @GetMapping("/courses/{id}")
    public Course getCourse(@PathVariable long id){
       return courseService.getCourseById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    // Add a course
    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody @Valid Course course){
        courseService.createCourse(course);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    // update a course

    // delete a course
}
