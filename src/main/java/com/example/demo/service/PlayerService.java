package com.example.demo.service;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.dao.PlayerRepository;
import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerRepository playerRepository1) {
        this.playerRepository = playerRepository;

    }

    public Player createPlayer(Player player){
       return playerRepository.createPlayer(player);
    }
    public Player updatePlayer(Long id, CreatePlayerRequest createPlayerRequest){
        Player player = new Player();
        player.setId(id);
        player.setName(createPlayerRequest.getName());
        player.setTitle(createPlayerRequest.getTitle());
        player.setRace(createPlayerRequest.getRace());
        player.setProfession(createPlayerRequest.getProfession());
        player.setExperience(createPlayerRequest.getExperience());
        player.setLevel(playerRepository.getPlayerById(id).getLevel());
        player.setBirthday(new Date(createPlayerRequest.getBirthday()));
        player.setBanned(createPlayerRequest.getBanned());
        return playerRepository.updatePlayer(player);
    }
    public Player deletePlayer(long id){
        return playerRepository.deletePlayer(id);
    }
    public Player getPlayerById(long id){
        return playerRepository.getPlayerById(id);
    }
    public List<Player> getPlayers(PlayerFilter playerFilter){
        return playerRepository.getPlayers(playerFilter);
    }
    public int getPlayersCount(PlayerFilter playerFilter){
        return playerRepository.getPlayersCount(playerFilter);
    }
}
