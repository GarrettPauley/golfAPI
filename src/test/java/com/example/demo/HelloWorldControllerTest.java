package com.example.demo;

import com.example.demo.controllers.GolferController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(GolferController.class)
class HelloWorldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /hello should return 'Hello World' with 200 status")
    public void shouldReturnHello() throws Exception{
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"));

    }

    @Test
    @DisplayName("GET /golfer should return JSON")
    public void shouldReturnGolferJSON() throws Exception{
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/golfers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jon Rahm"));

    }

}