package com.example.demo;

import com.example.demo.controllers.GolferController;
import com.example.demo.domain.Golfer;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.GolferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
@WebMvcTest - when a test method is executed, spring boot will load only the GolferController component into the
application context.

 */
@WebMvcTest(GolferController.class)
class GolferControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectmapper;
    @MockBean
    private GolferService golferService;

    String GOLFER_CONTROLLER_BASE = "/golfers";




    @Test
    //@DisplayName("GET /golfers/{id} should return a list of JSON objects with 200 status")
    public void GIVEN_userIsValid_THEN_return200WithJSONResponse() throws Exception{
        // GIVEN
        int id = 5;
        String name = "Happy Gilmore";
        String nationality = "American";
        int age = 30;
        int handicap = 4;
        Golfer happyGilmore = new Golfer(id, name, nationality, age,handicap);
        String uri = GOLFER_CONTROLLER_BASE + "/" + id;
        // WHEN and THEN
        Mockito.when(golferService.getGolferById(5)).thenReturn(happyGilmore);
        // ASSERT
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(uri))
                .andExpect(status().isOk()) // status code is 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // is JSON
                .andExpect(jsonPath("$", Matchers.not(hasSize(0))) ); //not empty

    }

    @Test
    public void  WHEN_userIsInvalid_THEN_return404() throws Exception{
        // GIVEN
        int fakeId = 12345;
        String nonExistURI = GOLFER_CONTROLLER_BASE  + fakeId;
        // WHEN & THEN
        Mockito.when(golferService.getGolferById(fakeId)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(nonExistURI))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void  WHEN_getGolfers_THEN_returnListOfGolfers_() throws Exception{
        // GIVEN - a few users exists in a list.
        Golfer fake1 = new Golfer(10, "Arnold Palmer", "American", 60, 4);
        Golfer fake2 = new Golfer(11, "Jack Nicholson", "American", 65, 3);
        Golfer fake3 = new Golfer(13, "Gary Player", "South African", 62, 2);
        List<Golfer> fakeList = Arrays.asList(fake1, fake2, fake3);
        // WHEN & THEN
        Mockito.when(golferService.getGolfers()).thenReturn(fakeList);
        mockMvc.perform(get(GOLFER_CONTROLLER_BASE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3))
                ).andDo(print());
    }

    @Test
    public void  GIVEN_golferObject_WHEN_PostGolfers_THEN_returnCreatedGolferWith201Status() throws Exception{
        // GIVEN - a few users exists in a list.
        Golfer g = Golfer.builder()
                .name("Gary Player")
                .age(75)
                .nationality("South African")
                .handicap(2)
                .build();

        // WHEN
        ResultActions response = mockMvc.perform(post("/golfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(g)));

        // THEN
       response.andDo(print())
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.name", is(g.getName())))
           .andExpect(jsonPath("$.age", is(g.getAge())))
           .andExpect(jsonPath("$.nationality", is(g.getNationality())))
           .andExpect(jsonPath("$.handicap", is(g.getHandicap())));

    }


    @Test
    public void  GIVEN_golferObjectNullName_WHEN_PostGolfers_THEN_return400BadRequest() throws Exception{
        // GIVEN - a few users exists in a list.
        Golfer g = Golfer.builder()
                .name(null)
                .age(75)
                .nationality("South African")
                .handicap(2)
                .build();

        // WHEN
        ResultActions response = mockMvc.perform(post("/golfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(g)));

        // THEN
        response.andDo(print())
                .andExpect(status().isBadRequest());

    }





}