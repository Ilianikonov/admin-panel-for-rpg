package com.example.demo.service;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;
import com.example.demo.dao.InMemoryPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlayerService {
    private final InMemoryPlayerRepository inMemoryPlayerRepository;

    public PlayerService(InMemoryPlayerRepository inMemoryPlayerRepository) {
        this.inMemoryPlayerRepository = inMemoryPlayerRepository;
    }

    public Player createPlayer(Player player){
       return inMemoryPlayerRepository.createPlayer(player);
    }
    public Player updatePlayer(Long id, CreatePlayerRequest createPlayerRequest){
        Player player = new Player();
        player.setId(id);
        player.setName(createPlayerRequest.getName());
        player.setTitle(createPlayerRequest.getTitle());
        player.setRace(createPlayerRequest.getRace());
        player.setProfession(createPlayerRequest.getProfession());
        player.setExperience(createPlayerRequest.getExperience());
        player.setLevel(inMemoryPlayerRepository.getPlayerById(id).getLevel());
        player.setBirthday(new Date(createPlayerRequest.getBirthday()));
        player.setBanned(createPlayerRequest.getBanned());
        return inMemoryPlayerRepository.updatePlayer(player);
    }
    public Player deletePlayer(long id){
        return inMemoryPlayerRepository.deletePlayer(id);
    }
    public Player getPlayerById(long id){
        return inMemoryPlayerRepository.getPlayerById(id);
    }
    public List<Player> getPlayers(PlayerFilter playerFilter){
        return inMemoryPlayerRepository.getPlayers(playerFilter);
    }
    public int getPlayersCount(PlayerFilter playerFilter){
        return inMemoryPlayerRepository.getPlayersCount(playerFilter);
    }
}
