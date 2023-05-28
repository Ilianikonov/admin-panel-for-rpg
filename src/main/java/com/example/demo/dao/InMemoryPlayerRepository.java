package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ConditionalOnProperty(name = "application.repository.type", havingValue = "inMemory")
public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map <Long, Player> players = new HashMap<>();

    @Override
    public void clear() {
        players.clear();
    }

    @Override
    public Player createPlayer(Player player) {
        long id = players.size();
        player.setId(id);
        players.put(id, player);
        return player;
    }

    @Override
    public Player updatePlayer(Player player) {
        players.put(player.getId(), player);
        return player;
    }

    @Override
    public Player deletePlayer(long id) {
        return players.remove(id);
    }

    @Override
    public Player getPlayerById(long id) {
        return players.get(id);
    }

    @Override
    public List<Player> getPlayers(PlayerFilter playerFilter) {
        return players.values().stream()
                .filter(player -> playerFilter.getName() == null || player.getName().contains(playerFilter.getName()))
                .filter(player -> playerFilter.getTitle() == null || player.getTitle().contains(playerFilter.getTitle()))
                .filter(player -> playerFilter.getRace() == null || player.getRace().equals(playerFilter.getRace()))
                .filter(player -> playerFilter.getProfession() == null || player.getProfession().equals(playerFilter.getProfession()))
                .filter(player -> playerFilter.getAfter() == null || playerFilter.getAfter() <= player.getBirthday().getTime())
                .filter(player -> playerFilter.getBefore() == null || playerFilter.getBefore() >= player.getBirthday().getTime())
                .filter(player -> playerFilter.getBanned() == null || player.getBanned().equals(playerFilter.getBanned()))
                .filter(player -> playerFilter.getMinExperience() == null || playerFilter.getMinExperience() <= player.getExperience())
                .filter(player -> playerFilter.getMaxExperience() == null || playerFilter.getMaxExperience() >= player.getExperience())
                .filter(player -> playerFilter.getMinLevel() == null || playerFilter.getMinLevel() <= player.getLevel())
                .filter(player -> playerFilter.getMaxLevel() == null || playerFilter.getMaxLevel() >= player.getLevel())
                .sorted(new ComparatorPlayer(playerFilter.getPlayerOrder()))
                .skip(playerFilter.getPageSize() * playerFilter.getPageNumber())
                .limit(playerFilter.getPageSize())
                .toList();
    }
    @Override
    public int getPlayersCount(PlayerFilter playerFilter) {
        return getPlayers(playerFilter).size();
    }
}
