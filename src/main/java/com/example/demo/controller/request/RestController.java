package com.example.demo.controller.request;

import com.example.demo.entity.Id;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerFilter;
import com.example.demo.filter.PlayerOrder;
import dao.InMemoryPlayerRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

public class RestController {
    InMemoryPlayerRepository inMemoryPlayerRepository = new InMemoryPlayerRepository();

    @GetMapping("/rest/players")
    public List<Player> getPlauersList(@RequestParam String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize){
        PlayerFilter playerFilter = new PlayerFilter(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        return inMemoryPlayerRepository.getPlayers(playerFilter);
    }
    @GetMapping("/rest/players/count")
    public int getPlayersCount(@RequestBody PlayerFilter playerFilter){
        return inMemoryPlayerRepository.getPlayersCount(playerFilter);
    }
    @PostMapping("/rest/player")
    public Player createPlayers (@RequestParam String name, String title, Race race, Profession profession, Long birthday, Boolean banned, Integer experience){
       CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        Player player = new Player();
        player.setName(name);
        player.setTitle(title);
        player.setRace(race);
        player.setProfession(profession);
        Date birthdayData = new Date(birthday);
        player.setBirthday(birthdayData);
        player.setExperience(experience);
        player.setBanned(banned);
        return inMemoryPlayerRepository.createPlayer(player);
    }
    @GetMapping("/rest/players/{id}")
    public Player getPlayer(@RequestBody Id id){
        return inMemoryPlayerRepository.getPlayerById(id.getId());
    }
    @PostMapping("/rest/players/{id}")
    public Player updatePlayer (@RequestBody Id id, @RequestParam String name, String title, Race race, Profession profession, Long birthday, Boolean banned, Integer experience){
        inMemoryPlayerRepository.getPlayerById(id.getId()).setName(name);
        inMemoryPlayerRepository.getPlayerById(id.getId()).setTitle(title);
        inMemoryPlayerRepository.getPlayerById(id.getId()).setRace(race);
        inMemoryPlayerRepository.getPlayerById(id.getId()).setProfession(profession);
        inMemoryPlayerRepository.getPlayerById(id.getId()).setBirthday(new Date(birthday));
        inMemoryPlayerRepository.getPlayerById(id.getId()).setBanned(banned);
        inMemoryPlayerRepository.getPlayerById(id.getId()).setExperience(experience);
        return inMemoryPlayerRepository.updatePlayer(inMemoryPlayerRepository.getPlayerById(id.getId()));
    }
    @DeleteMapping("/rest/players/{id}")
    public void deletePlayer (@RequestBody Id id){
        inMemoryPlayerRepository.deletePlayer(id.getId());
    }
}
