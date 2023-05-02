package com.example.demo.controllers;

import com.example.demo.domain.Course;
import com.example.demo.exceptions.CourseNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.CourseService;
import org.h2.engine.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

    // update a course

    // delete a course
}
