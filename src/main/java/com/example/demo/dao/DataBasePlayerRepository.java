package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DataBasePlayerRepository implements PlayerRepository{
    private final JdbcTemplate jdbcTemplate;

    public DataBasePlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Player createPlayer(Player player) {
        String sql;
        jdbcTemplate.execute(sql);
        return null;
    }

    @Override
    public Player updatePlayer(Player player) {
        return null;
    }

    @Override
    public Player deletePlayer(long id) {
        return null;
    }

    @Override
    public Player getPlayerById(long id) {
        return null;
    }

    @Override
    public List<Player> getPlayers(PlayerFilter playerFilter) {
        return null;
    }

    @Override
    public int getPlayersCount(PlayerFilter playerFilter) {
        return 0;
    }
}
