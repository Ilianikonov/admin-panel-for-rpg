package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;
import dao.InMemoryPlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UiController {
    InMemoryPlayerRepository inMemoryPlayerRepository = new InMemoryPlayerRepository();
    @GetMapping("/rest/players")
    public List<Player> getPlauersList(PlayerFilter playerFilter){
        return inMemoryPlayerRepository.getPlayers(playerFilter);
    }
    @GetMapping("/rest/players/count")
    public int getPlayersCount(@RequestBody PlayerFilter playerFilter, @RequestParam String number, @RequestParam String id){
    return inMemoryPlayerRepository.getPlayersCount(playerFilter);
    }
    @PostMapping("/rest/player")
    public Player createPlayers (@RequestBody CreatePlayerRequest createPlayerRequest){
        Player player = new Player();
        player.setName(createPlayerRequest.getName());
        player.setTitle(createPlayerRequest.getTitle());
        player.setRace(createPlayerRequest.getRace());
        player.setProfession(createPlayerRequest.getProfession());
        player.setBirthday(createPlayerRequest.getBirthday());
        return inMemoryPlayerRepository.createPlayer(player);
    }
    @GetMapping("/rest/players/{id}")
    public Player getPlayer(@RequestParam int id){
        return inMemoryPlayerRepository.getPlayerById(id);
    }
    @PostMapping("/rest/players/{id}")
    public Player updatePlayer (@RequestParam int id){
        return inMemoryPlayerRepository.updatePlayer(inMemoryPlayerRepository.getPlayerById(id));
    }
    @DeleteMapping("/rest/players/{id}")
    public void deletePlayer (@RequestParam long id){
        inMemoryPlayerRepository.deletePlayer(id);
    }
}
