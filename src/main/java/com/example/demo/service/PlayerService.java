package com.example.demo.service;

import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;
import com.example.demo.dao.InMemoryPlayerRepository;
import org.springframework.stereotype.Service;

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
    public Player updatePlayer(Player player){
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
