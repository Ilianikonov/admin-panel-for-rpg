package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerFilter;
import com.example.demo.filter.PlayerOrder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map <Long, Player> players = new HashMap<>();

    public InMemoryPlayerRepository() {
        Player player = new Player();
        player.setName("Ilia");
        player.setRace(Race.HUMAN);
        player.setProfession(Profession.ROGUE);
        player.setBirthday(new Date());
        player.setExperience(100);
        player.setLevel(2);
        createPlayer(player);
        Player player1 = new Player();
        player1.setName("Ilya");
        player1.setRace(Race.HOBBIT);
        player1.setProfession(Profession.WARLOCK);
        player1.setBirthday(new Date());
        player1.setExperience(1000);
        player1.setLevel(3);
        createPlayer(player1);
        Player player3 = new Player();
        player3.setName("Lin");
        player3.setRace(Race.GIANT);
        player3.setProfession(Profession.ROGUE);
        player3.setBirthday(new Date());
        player3.setExperience(55);
        player3.setLevel(5);
        createPlayer(player3);
        players.put(2l,player3);
        Player player2 = new Player();
        player2.setName("Igglya");
        player2.setRace(Race.HOBBIT);
        player2.setProfession(Profession.WARLOCK);
        player2.setBirthday(new Date());
        player2.setExperience(1010);
        player2.setLevel(33);
        createPlayer(player2);
        Player player11 = new Player();
        player11.setName("Ilewgegya");
        player11.setRace(Race.HOBBIT);
        player11.setProfession(Profession.WARLOCK);
        player11.setBirthday(new Date());
        player11.setExperience(10100);
        player11.setLevel(3);
        createPlayer(player11);
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
                .filter(player -> playerFilter.getName() == null || player.getName().equals(playerFilter.getName()))
                .filter(player -> playerFilter.getTitle() == null || player.getTitle().equals(playerFilter.getTitle()))
                .filter(player -> playerFilter.getRace() == null || player.getRace().equals(playerFilter.getRace()))
                .filter(player -> playerFilter.getProfession() == null || player.getProfession().equals(playerFilter.getProfession()))
                .filter(player -> playerFilter.getAfter() == null || playerFilter.getAfter() <= player.getBirthday().getTime())
                .filter(player -> playerFilter.getBefore() == null || playerFilter.getBefore() >= player.getBirthday().getTime())
                .filter(player -> playerFilter.getBanned() == null || player.getBanned().equals(playerFilter.getBanned()))
                .filter(player -> playerFilter.getMinExperience() == null || playerFilter.getMinExperience() <= player.getExperience())
                .filter(player -> playerFilter.getMaxExperience() == null || playerFilter.getMaxExperience() >= player.getExperience())
                .filter(player -> playerFilter.getMinLevel() == null || playerFilter.getMinLevel() <= player.getLevel())
                .filter(player -> playerFilter.getMaxLevel() == null || playerFilter.getMaxLevel() >= player.getLevel())
                .toList();
    }
    @Override
    public int getPlayersCount(PlayerFilter playerFilter) {
        return getPlayers(playerFilter).size();
    }
}
