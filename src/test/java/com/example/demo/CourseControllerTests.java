package com.example.demo;



import com.example.demo.controllers.CourseController;
import com.example.demo.controllers.GolferController;
import com.example.demo.domain.Course;
import com.example.demo.domain.Golfer;
import com.example.demo.exceptions.CourseNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.CourseService;
import com.example.demo.service.GolferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.DecimalMin;
import org.aspectj.weaver.patterns.ReferencePointcut;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
@WebMvcTest - when a test method is executed, spring boot will load only the GolferController component into the
application context.

 */
@WebMvcTest(CourseController.class)
public class CourseControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectmapper;
    @MockBean
    private CourseService courseService;
    String COURSES_CONTROLLER_BASE = "/courses";


   @Test
   @DisplayName("GET /courses returns list of courses and 200 status code")
    public void WHEN_getCourses_THEN_returnListOfCourses() throws Exception{
       // GIVEN - a few users exists in a list.
       Course fake1 = new Course(123, "The hills at IDC", 32, 60, 4, 6800, 5.0f);
       Course fake2 = new Course(124, "The links of who cares", 30, 65, 3,7000, 3.4f);
       List<Course> fakeList = Arrays.asList(fake1, fake2);
       // WHEN & THEN
       when(courseService.getCourses()).thenReturn(fakeList);
       mockMvc.perform(get(COURSES_CONTROLLER_BASE))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2))
               ).andDo(print());
   }

   @Test
   @DisplayName("GET /courses/{id} returns one course with status 200")
    public void WHEN_getCoursesById_THEN_returnOneCourseWith200Response() throws Exception{

       Course fakeCourse = new Course(124, "The links of who cares", 30, 65, 3,7000, 3.4f);
       when(courseService.getCourseById(anyLong())).thenReturn(Optional.of(fakeCourse));

       mockMvc.perform(get(COURSES_CONTROLLER_BASE + "/" + fakeCourse.getId()))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

   }

    @Test
    @DisplayName("GET /courses/{invalidId} throws CourseNotFoundError")
    public void GIVEN_invalidID_WHEN_getCoursesById_THEN_returnCourseNotFoundResponse() throws Exception {

        long someID = anyLong();
        when(courseService.getCourseById(someID)).thenThrow(CourseNotFoundException.class);
        String uri = COURSES_CONTROLLER_BASE + "/" + someID;
                mockMvc.perform(get(uri))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CourseNotFoundException));
    }

    @Test
    @DisplayName("POST /courses with valid body should create course")
    public void GIVEN_validbody_WHEN_postrequest_THEN_createCourse() throws Exception{
        Course course = new Course( 10,"Some nice course", 35.5f, 60.3f, 70, 6800, 3.4f);
        // WHEN
        ResultActions response = mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(course)));

       response.andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /courses with invalid body should return a 400 bad request response")
    public void GIVEN_invalidBody_WHEN_postrequest_THEN_createCourse() throws Exception{
        // latitude must be between -90 and 90.
        Course course = new Course(10, "Some fictitious course", 180f, 60.3f, 70, 6800, 3.4f);

        mockMvc.perform(post("/courses"))
                .andExpect(status().isBadRequest());
    }




}
