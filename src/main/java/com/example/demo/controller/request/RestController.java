package com.example.demo.controller.request;

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
                                       @RequestParam(defaultValue = "ID") PlayerOrder order,
                                       @RequestParam(defaultValue = "0") Integer pageNumber,
                                       @RequestParam(defaultValue = "3") Integer pageSize){
        PlayerFilter playerFilter = new PlayerFilter(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
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
        return playerService.getPlayersCount(new PlayerFilter(name,title,race,profession,after,before,banned,minExperience,maxExperience,minLevel,maxLevel, null, null,null));
    }
    @PostMapping("/rest/players")
    public Player createPlayer (@RequestBody CreatePlayerRequest createPlayerRequest){
        Player player = new Player();
        player.setName(createPlayerRequest.getName());
        player.setTitle(createPlayerRequest.getTitle());
        player.setRace(createPlayerRequest.getRace());
        player.setProfession(createPlayerRequest.getProfession());
        player.setBirthday(new Date(createPlayerRequest.birthday));
        player.setExperience(createPlayerRequest.getExperience());
        player.setBanned(createPlayerRequest.getBanned());
        return playerService.createPlayer(player);
    }
    @GetMapping("/rest/players/{id}")
    public Player getPlayer(@RequestParam Long id){
        System.out.println(playerService.getPlayerById(id));
        return playerService.getPlayerById(id);
    }
    @PostMapping("/rest/players/{id}")
    public Player updatePlayer (@RequestParam Long id, @RequestBody CreatePlayerRequest createPlayerRequest){
        playerService.getPlayerById(id).setName(createPlayerRequest.getName());
        playerService.getPlayerById(id).setTitle(createPlayerRequest.getTitle());
        playerService.getPlayerById(id).setRace(createPlayerRequest.getRace());
        playerService.getPlayerById(id).setProfession(createPlayerRequest.getProfession());
        playerService.getPlayerById(id).setBirthday(new Date(createPlayerRequest.birthday));
        playerService.getPlayerById(id).setBanned(createPlayerRequest.getBanned());
        playerService.getPlayerById(id).setExperience(createPlayerRequest.getExperience());
        return playerService.updatePlayer(playerService.getPlayerById(id));
    }
    @DeleteMapping("/rest/players/{id}")
    public void deletePlayer (@RequestParam Long id){
        playerService.deletePlayer(id);
    }
}
