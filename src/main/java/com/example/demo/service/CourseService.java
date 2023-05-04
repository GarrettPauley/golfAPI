package com.example.demo.service;

import com.example.demo.domain.Course;
import com.example.demo.exceptions.CourseNotFoundException;
import com.example.demo.repositories.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
   private CourseRepository repository;

   public CourseService(CourseRepository _courseRepository){
       this.repository = _courseRepository;
   }

   public List<Course> getCourses(){
       return repository.findAll();
   }

   public Optional<Course> getCourseById(long id){
       return Optional.of(repository.findById(id)).orElseThrow(() -> new CourseNotFoundException(id));
   }

   public Course createCourse(Course course){
       return repository.save(course);
   }

}
