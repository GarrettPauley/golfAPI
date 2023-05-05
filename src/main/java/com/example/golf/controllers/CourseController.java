package com.example.golf.controllers;

import com.example.golf.domain.Course;
import com.example.golf.exceptions.CourseNotFoundException;
import com.example.golf.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable long  id){
        courseService.deleteCourse(id);

    }

    // update a course

    // delete a course
}
