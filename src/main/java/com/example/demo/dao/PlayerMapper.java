package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {
    @Override
    public Player mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Player player = new Player();
        player.setId(resultSet.getLong("id"));
        player.setName(resultSet.getString("player_name"));
        player.setTitle(resultSet.getString("title"));
        player.setRace(Race.valueOf(resultSet.getString("race_name")));
        player.setProfession(Profession.valueOf(resultSet.getString("profession_name")));
        player.setExperience(resultSet.getInt("experience"));
        player.setLevel(resultSet.getInt("level"));
        player.setUntilNextLevel(resultSet.getInt("until_next_level"));
        player.setBirthday(resultSet.getDate("birthday"));
        player.setBanned(resultSet.getBoolean("banned"));
        return player;
    }
}
