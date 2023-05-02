package com.example.demo;

import com.example.demo.controllers.GolferController;
import com.example.demo.domain.Golfer;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.GolferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.mockito.Mockito.when;
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
    @Order(1)
    @DisplayName("GET /golfers/{id} : return golfer and 200 ")
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
        when(golferService.getGolferById(5)).thenReturn(Optional.of(happyGilmore));
        // ASSERT
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(uri))
                .andExpect(status().isOk()) // status code is 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // is JSON
                .andExpect(jsonPath("$", Matchers.not(hasSize(0))) ); //not empty

    }

    @Test
    @Order(2)
    @DisplayName("GET: return 404 for invalid id")
    public void  WHEN_userIsInvalid_THEN_return404() throws Exception{
        // GIVEN
        int fakeId = 12345;
        String nonExistURI = GOLFER_CONTROLLER_BASE  + fakeId;
        // WHEN & THEN
        when(golferService.getGolferById(fakeId)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(nonExistURI))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @Order(3)
    @DisplayName("GET: return list of golfers ")
    public void  WHEN_getGolfers_THEN_returnListOfGolfers_() throws Exception{
        // GIVEN - a few users exists in a list.
        Golfer fake1 = new Golfer(10, "Arnold Palmer", "American", 60, 4);
        Golfer fake2 = new Golfer(11, "Jack Nicholson", "American", 65, 3);
        Golfer fake3 = new Golfer(13, "Gary Player", "South African", 62, 2);
        List<Golfer> fakeList = Arrays.asList(fake1, fake2, fake3);
        // WHEN & THEN
        when(golferService.getGolfers()).thenReturn(fakeList);
        mockMvc.perform(get(GOLFER_CONTROLLER_BASE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3))
                ).andDo(print());
    }

    @Test
    @Order(4)
    @DisplayName("POST: given valid golfer - return 201 created status")
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
    @Order(5)
    @DisplayName("POST: blank name returns 400 bad request")
    public void  GIVEN_golferObjectBlankName_WHEN_PostGolfers_THEN_return400BadRequest() throws Exception{
        // GIVEN - a few users exists in a list.
        Golfer g = Golfer.builder()
                .name("")
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



    @Test
    @Order(6)
    @DisplayName("POST: missing name returns 400")
    public void GIVEN_JSONwithMissingName_WHEN_POSTgolfers_THEN_return400() throws Exception {
        Golfer g = Golfer.builder()
                .age(24)
                .nationality("American")
                .handicap(9).build();

        when(golferService.updateGolfer(g)).thenThrow(HttpClientErrorException.BadRequest.class);
        mockMvc.perform(post("/golfers")).andExpect(status().isBadRequest());


    }


    @Test
    @Order(7)
    @DisplayName("UPDATE: given valid JSON body - return 200 and update golfer JSON")
    public void  GIVEN_golferID_WHEN_UpdateGolfer_THEN_return200WithUpdatedGolfer() throws Exception{
        Golfer g = Golfer.builder()
                .age(24)
                .name("Garrett Pauley")
                .nationality("American")
                .handicap(9).build();
        golferService.saveGolfer(g);
        Golfer updatedG = Golfer.builder()
                .age(24)
                .name("Garrett Pauley")
                .nationality("American")
                .handicap(10).build();
        when(golferService.updateGolfer(updatedG)).thenReturn(updatedG);

        ResultActions response =
                mockMvc.perform(put("/golfers/{id}", anyInt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(updatedG))
                );
        // THEN
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.handicap", is(10)));

    }


    @Test
    @Order(8)
    @DisplayName("DELETE: valid id returns 200 ")
    public void GIVEN_golferExists_WHEN_DeleteGolfer_THEN_deleteAndReturn200() throws UserNotFoundException{
        Golfer g = Golfer.builder()
                .id(20)
                .age(24)
                .name("Garrett Pauley")
                .nationality("American")
                .handicap(9).build();
        golferService.deleteGolfer(g.getId());
        Mockito.verify(golferService).deleteGolfer(g.getId());
    }

    @Test
    @Order(9)
    @DisplayName("DELETE: non-exist id returned 404")
    public void  GIVEN_golferIDNonExist_WHEN_DeleteGolfers_THEN_return404() throws Exception{
        int someId = 1;
        Mockito.doThrow(new UserNotFoundException(someId)).when(golferService).deleteGolfer(someId);
        mockMvc.perform(delete("/golfers/{id}", someId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @Order(11)
    @DisplayName("DELETE: given valid id - delete golfer and return 200")
    public void  GIVEN_golferID_WHEN_DeleteGolfers_THEN_return200() throws Exception{

        // WHEN
        ResultActions response = mockMvc.perform(delete("/golfers/1")
                .contentType(MediaType.APPLICATION_JSON));

        // THEN
        response.andDo(print())
                .andExpect(status().isOk());
        assertTrue(golferService.getGolferById(1).isEmpty());
    }


    @Test
    @Order(12)
    @DisplayName("DELETE: non-exist id returned UserNotFoundException")
    public void  GIVEN_golferIDNonExist_WHEN_DeleteGolfers_THENTHROW_UserNotFound() throws Exception {
        int someId = 1;
        Mockito.doThrow(new UserNotFoundException(someId)).when(golferService).deleteGolfer(someId);
        mockMvc.perform(delete("/golfers/{id}", someId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andDo(print());
    }




}