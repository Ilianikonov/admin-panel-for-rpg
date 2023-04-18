package com.example.demo;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void createPlayerTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setName("Ilia");
        createPlayerRequest.setTitle("non");
        createPlayerRequest.setProfession(Profession.ROGUE);
        createPlayerRequest.setRace(Race.HUMAN);
        createPlayerRequest.setBirthday(420402400L);
        createPlayerRequest.setExperience(1000);
        createPlayerRequest.setBanned(false);
        mockMvc.perform(post("/rest/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPlayerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Ilia"))
                .andExpect(jsonPath("$.title").value("non"))
                .andExpect(jsonPath("$.race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$.profession").value(Profession.ROGUE.name()))
                .andExpect(jsonPath("$.birthday").value(420402400L))
                .andExpect(jsonPath("$.banned").value(false))
                .andExpect(jsonPath("$.experience").value(1000))
                .andExpect(jsonPath("$.level").value(4))
                .andExpect(jsonPath("$.untilNextLevel").value(500));
    }
    @Test
    public void getPlayerTest(){
    }
    @Test
    public void getPlayersListTest(){

    }
}
