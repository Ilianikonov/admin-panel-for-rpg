package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.response.PlayersResponse;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerFilter;
import com.example.demo.filter.PlayerOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.PlayerService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest/players")
public class RestController {
    private  final PlayerService playerService;


    public RestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayersResponse> getPlayersList(@RequestParam(required = false) String name,
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
                                                @RequestParam(defaultValue = "3") Long pageSize){
        PlayerFilter playerFilter = new PlayerFilter(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
        return  playerService.getPlayers(playerFilter).stream()
                .map(this::convertToPlayerResponse)
                .toList();
    }
    @GetMapping("/count")
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
        return playerService.getPlayersCount(new PlayerFilter(name,title,race,profession,after,before,banned,minExperience,maxExperience,minLevel,maxLevel, PlayerOrder.ID, 0, Long.MAX_VALUE));
    }
    @PostMapping
    public PlayersResponse createPlayer (@RequestBody @Valid CreatePlayerRequest createPlayerRequest){
        Player player = new Player();
        player.setName(createPlayerRequest.getName());
        player.setTitle(createPlayerRequest.getTitle());
        player.setRace(createPlayerRequest.getRace());
        player.setProfession(createPlayerRequest.getProfession());
        player.setBirthday(new Date(createPlayerRequest.getBirthday()));
        player.setExperience(createPlayerRequest.getExperience());
        player.setBanned(createPlayerRequest.getBanned());
        return convertToPlayerResponse(playerService.createPlayer(player));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PlayersResponse> getPlayer(@PathVariable Long id){
        if (playerService.getPlayerById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToPlayerResponse(playerService.getPlayerById(id)), HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<PlayersResponse> updatePlayer (@PathVariable Long id, @RequestBody CreatePlayerRequest createPlayerRequest){
        if (playerService.getPlayerById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToPlayerResponse(playerService.updatePlayer(id,createPlayerRequest)), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer (@PathVariable Long id){
      if (playerService.deletePlayer(id) == null){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(HttpStatus.OK);
    }

    private PlayersResponse convertToPlayerResponse(Player player){
        return PlayersResponse.builder()
                .name(player.getName())
                .title(player.getTitle())
                .id(player.getId())
                .race(player.getRace())
                .profession(player.getProfession())
                .experience(player.getExperience())
                .level(player.getLevel())
                .birthday(player.getBirthday().getTime())
                .untilNextLevel(player.getUntilNextLevel())
                .banned(player.getBanned())
                .build();
    }
}
