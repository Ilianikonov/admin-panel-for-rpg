package com.example.demo.controller.request;

import com.example.demo.entity.Id;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerFilter;
import com.example.demo.filter.PlayerOrder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.PlayerService;

import java.util.Date;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private  final PlayerService playerService;

    public RestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/rest/players")
    public List<Player> getPlauersList(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) Race race,
                                       @RequestParam(required = false) Profession profession,
                                       @RequestParam(required = false) Long after,
                                       @RequestParam(required = false) Long before,
                                       @RequestParam(required = false) Boolean banned,
                                       @RequestParam(required = false) Integer minExperience,
                                       @RequestParam(required = false) Integer maxExperience,
                                       @RequestParam(required = false) Integer minLevel,
                                       @RequestParam(required = false) Integer maxLevel,
                                       @RequestParam(defaultValue = "id") PlayerOrder order,
                                       @RequestParam(defaultValue = "0") Integer pageNumber,
                                       @RequestParam(defaultValue = "3") Integer pageSize){
        PlayerFilter playerFilter = new PlayerFilter(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        return playerService.getPlayers(playerFilter);
    }
    @GetMapping("/rest/players/count")
    public int getPlayersCount(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) Race race,
                               @RequestParam(required = false) Profession profession,
                               @RequestParam(required = false) Long after,
                               @RequestParam(required = false) Long before,
                               @RequestParam(required = false) Boolean banned,
                               @RequestParam(required = false) Integer minExperience,
                               @RequestParam(required = false) Integer maxExperience,
                               @RequestParam(required = false) Integer minLevel,
                               @RequestParam(required = false) Integer maxLevel){
        return playerService.getPlayersCount(new PlayerFilter(name,title,race,profession,after,before,banned,minExperience,maxExperience,minLevel,maxLevel));
    }
    @PostMapping("/rest/player")
    public Player createPlayers (@RequestParam() String name, String title, Race race, Profession profession, Long birthday, Boolean banned, Integer experience){
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
        return playerService.createPlayer(player);
    }
    @GetMapping("/rest/players/{id}")
    public Player getPlayer(@RequestBody Id id){
        return playerService.getPlayerById(id.getId());
    }
    @PostMapping("/rest/players/{id}")
    public Player updatePlayer (@RequestBody Id id, @RequestParam String name, String title, Race race, Profession profession, Long birthday, Boolean banned, Integer experience){
        playerService.getPlayerById(id.getId()).setName(name);
        playerService.getPlayerById(id.getId()).setTitle(title);
        playerService.getPlayerById(id.getId()).setRace(race);
        playerService.getPlayerById(id.getId()).setProfession(profession);
        playerService.getPlayerById(id.getId()).setBirthday(new Date(birthday));
        playerService.getPlayerById(id.getId()).setBanned(banned);
        playerService.getPlayerById(id.getId()).setExperience(experience);
        return playerService.updatePlayer(playerService.getPlayerById(id.getId()));
    }
    @DeleteMapping("/rest/players/{id}")
    public void deletePlayer (@RequestBody Id id){
        playerService.deletePlayer(id.getId());
    }
}
