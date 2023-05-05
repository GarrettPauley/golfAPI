package com.example.golf.service;

import com.example.golf.domain.Course;
import com.example.golf.exceptions.CourseNotFoundException;
import com.example.golf.repositories.CourseRepository;
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

   public void deleteCourse(long id){
       repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
       repository.deleteById(id);
   }

}
