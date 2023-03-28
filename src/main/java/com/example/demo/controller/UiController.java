package com.example.demo.controller;

import dao.InMemoryPlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UiController {
    InMemoryPlayerRepository inMemoryPlayerRepository = new InMemoryPlayerRepository();
    @DeleteMapping("/rest/players/{id}")
    public void deletePlayer (@RequestParam long id){
        inMemoryPlayerRepository.deletePlayer(id);
    }
}
