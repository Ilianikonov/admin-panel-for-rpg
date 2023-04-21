package com.example.demo;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.response.PlayersResponse;
import com.example.demo.dao.InMemoryPlayerRepository;
import com.example.demo.dao.PlayerRepository;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerFilter;
import com.example.demo.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

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
    @Autowired
    private PlayerRepository playerRepository;
    @BeforeEach
    public void beforeEach() throws Exception {
        createPlayer("ilia",Race.HUMAN);
        createPlayer("Alex", Race.DWARF);
        createPlayer("lin", Race.ORC);
    }
    @AfterEach
    public void afterEach(){
        playerRepository.clear();
    }
    @Test
    public void createPlayerTest() throws Exception {
        createPlayer("Ilia", Race.HUMAN)
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
    public void getPlayerTest() throws Exception {
        Long id = 0L;
        mockMvc.perform(get("/rest/players/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("ilia"))
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
    public void getPlayersListTest() throws Exception {
        PlayerFilter playerFilter = new PlayerFilter(null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        mockMvc.perform(get("/rest/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("ilia"))
                .andExpect(jsonPath("$[0].title").value("non"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.ROGUE.name()))
                .andExpect(jsonPath("$[0].birthday").value(420402400L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("Alex"))
                .andExpect(jsonPath("$[1].title").value("non"))
                .andExpect(jsonPath("$[1].race").value(Race.DWARF.name()))
                .andExpect(jsonPath("$[1].profession").value(Profession.ROGUE.name()))
                .andExpect(jsonPath("$[1].birthday").value(420402400L))
                .andExpect(jsonPath("$[1].banned").value(false))
                .andExpect(jsonPath("$[1].experience").value(1000))
                .andExpect(jsonPath("$[1].level").value(4))
                .andExpect(jsonPath("$[1].untilNextLevel").value(500))
                .andExpect(jsonPath("$[2].id").isNumber())
                .andExpect(jsonPath("$[2].name").value("lin"))
                .andExpect(jsonPath("$[2].title").value("non"))
                .andExpect(jsonPath("$[2].race").value(Race.ORC.name()))
                .andExpect(jsonPath("$[2].profession").value(Profession.ROGUE.name()))
                .andExpect(jsonPath("$[2].birthday").value(420402400L))
                .andExpect(jsonPath("$[2].banned").value(false))
                .andExpect(jsonPath("$[2].experience").value(1000))
                .andExpect(jsonPath("$[2].level").value(4))
                .andExpect(jsonPath("$[2].untilNextLevel").value(500));
    }
    @Test
    public void deletePlayerTest() throws Exception {
        Long id = 0L;
        mockMvc.perform(delete("/rest/players/{id}",id))
                .andExpect(status().isOk());
    }
    private ResultActions createPlayer(String name, Race race) throws Exception {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setName(name);
        createPlayerRequest.setTitle("non");
        createPlayerRequest.setProfession(Profession.ROGUE);
        createPlayerRequest.setRace(race);
        createPlayerRequest.setBirthday(420402400L);
        createPlayerRequest.setExperience(1000);
        createPlayerRequest.setBanned(false);
        return mockMvc.perform(post("/rest/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPlayerRequest)));
    }
}
