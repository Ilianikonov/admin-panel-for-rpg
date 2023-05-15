package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {
ResultSet resultSet;
    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        Player player = new Player();
        player.setId((long) resultSet.getInt("id"));
        player.setName(resultSet.getString("name"));
        player.setTitle(resultSet.getString("title"));
        player.setRace(getRaceId(resultSet.getInt("race")));
        player.setProfession(getProfessionId(resultSet.getInt("profession")));
        player.setExperience(resultSet.getInt("experience"));
        player.setLevel(resultSet.getInt("level"));
        player.setUntilNextLevel(resultSet.getInt("until_next_level"));
        player.setBirthday(resultSet.getDate("birthday"));
        player.setBanned(resultSet.getBoolean("banned"));
        return player;
    }
    public Race getRaceId(int raceId){
        if   (raceId == 1){
            return Race.HUMAN;
        } else if (raceId == 2){
            return Race.DWARF;
        } else if (raceId == 3){
            return Race.ELF;
        } else  if (raceId == 4){
            return Race.GIANT;
        } else if (raceId == 5){
            return Race.ORC;
        } else if (raceId == 6){
            return Race.TROLL;
        } else {
            return Race.HOBBIT;
        }
    }
    public Profession getProfessionId(int professionId) {
        if (professionId == 1) {
            return Profession.WARRIOR;
        } else if (professionId == 2) {
            return Profession.ROGUE;
        } else if (professionId == 3) {
            return Profession.SORCERER;
        } else if (professionId == 4) {
            return Profession.CLERIC;
        } else if (professionId == 5) {
            return Profession.PALADIN;
        } else if (professionId == 6) {
            return Profession.NAZGUL;
        } else if (professionId == 7) {
            return Profession.WARLOCK;
        } else {
            return Profession.DRUID;
        }
    }
}
