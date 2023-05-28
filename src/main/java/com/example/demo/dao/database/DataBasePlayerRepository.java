package com.example.demo.dao.database;

import com.example.demo.dao.PlayerRepository;
import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@ConditionalOnProperty(name = "application.repository.type", havingValue = "database")
public class DataBasePlayerRepository implements PlayerRepository {
    private final JdbcTemplate jdbcTemplate;
    public DataBasePlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void clear() {
        jdbcTemplate.update("truncate player");
    }

    @Override
   public Player createPlayer(Player player) {
        long playerID = jdbcTemplate.queryForObject("INSERT INTO player(name,title,race_id,profession_id,experience,level,until_next_level,birthday,banned) VALUES (?,?,(select race.id from race where race.name = ?),(select profession.id from profession where profession.name = ?),?,?,?,?,?) RETURNING player.id", new Object[]{player.getName(), player.getTitle(), player.getRace().name(), player.getProfession().name(), player.getExperience(), player.getLevel(), player.getUntilNextLevel(), player.getBirthday(), player.getBanned()}, Long.class);
        return jdbcTemplate.queryForObject("SELECT player.id, player.name player_name, title, race.name race_name, profession.name profession_name, experience, level, until_next_level, birthday, banned FROM player WHERE player.id = " + playerID,
                new PlayerMapper());
    }

    @Override
    public Player updatePlayer(Player player) {
        jdbcTemplate.update("UPDATE player SET name=?, title=?, race_id=?, profession_id=?, experience=?, level=?, until_next_level=?, birthday=?, banned=? WHERE player.id = ?",
                player.getName(), player.getTitle(), playerGetRaceId(player), playerGetProfessionId(player), player.getExperience(), player.getLevel(), player.getUntilNextLevel(), player.getBirthday(), player.getBanned(), player.getId());
        return getPlayerById(player.getId());
    }

    @Override
    public Player deletePlayer(long id) {
        Player player = getPlayerById(id);
        jdbcTemplate.update("DELETE FROM player WHERE id = ?  ", id);
        return player;
    }

    @Override
    public Player getPlayerById(long id) {
        return jdbcTemplate.queryForObject("SELECT player.id, player.name player_name, title, race.name race_name, profession.name profession_name, experience, level, until_next_level, birthday, banned FROM player inner join race on player.race_id = race.id inner join profession on player.profession_id = profession.id WHERE player.id = ?", new Object[]{id},new PlayerMapper());
    }

    @Override
    public List<Player> getPlayers(PlayerFilter playerFilter) {
        SqlBuilder sqlBuilder = new SqlBuilder("player.id, player.name player_name, title, race.name race_name, profession.name profession_name, experience, level, until_next_level, birthday, banned",
                "player inner join race on player.race_id = race.id inner join profession on player.profession_id = profession.id");
        sqlBuilder.orderBy("player." + playerFilter.getPlayerOrder().getFieldName());
        sqlBuilder.offset(playerFilter.getPageSize() * playerFilter.getPageNumber());
        sqlBuilder.fetchNext(playerFilter.getPageSize());
        return jdbcTemplate.query(getFilterPlayerSQL(playerFilter,sqlBuilder).build(), new PlayerMapper());
    }
    private SqlBuilder getFilterPlayerSQL(PlayerFilter playerFilter, SqlBuilder sqlBuilder){
        if (playerFilter.getName() != null) {
            sqlBuilder.where("player.name = '" + playerFilter.getName() + "'");
        }
        if (playerFilter.getTitle() != null) {
            sqlBuilder.where("player.title = '" + playerFilter.getTitle() + "'");
        }
        if (playerFilter.getRace() != null) {
            sqlBuilder.where("race.name = '" + playerFilter.getRace() + "'");
        }
        if (playerFilter.getProfession() != null) {
            sqlBuilder.where("profession.name = '" + playerFilter.getProfession() + "'");
        }
        if (playerFilter.getAfter() != null) {
            sqlBuilder.where("player.birthday <= '" + playerFilter.getAfter() + "'");
        }
        if (playerFilter.getBefore() != null) {
            sqlBuilder.where("player.birthday >= '" + playerFilter.getBefore() + "'");
        }
        if (playerFilter.getBanned() != null) {
            sqlBuilder.where("player.banned = '" + playerFilter.getBanned() + "'");
        }
        if (playerFilter.getMinExperience() != null) {
            sqlBuilder.where("player.experience >= '" + playerFilter.getMinExperience() + "'");
        }
        if (playerFilter.getMaxExperience() != null) {
            sqlBuilder.where("player.experience <= '" + playerFilter.getMaxExperience() + "'");
        }
        if (playerFilter.getMinLevel() != null) {
            sqlBuilder.where("player.level >= '" + playerFilter.getMinLevel() + "'");
        }
        if (playerFilter.getMaxLevel() != null) {
            sqlBuilder.where("player.level <= '" + playerFilter.getMaxLevel() + "'");
        }
        return sqlBuilder;
    }

    @Override
    public int getPlayersCount(PlayerFilter playerFilter) {
        SqlBuilder sqlBuilder = new SqlBuilder("count(player.id)","player inner join race on player.race_id = race.id inner join profession on player.profession_id = profession.id");
        return jdbcTemplate.queryForObject(getFilterPlayerSQL(playerFilter,sqlBuilder).build(),Integer.class);
    }
    private int playerGetRaceId(Player player){
       return jdbcTemplate.queryForObject("select id from race where race.name = '" + player.getRace() + "'", Integer.class);
    }
    private int playerGetProfessionId(Player player){
            return jdbcTemplate.queryForObject("select id from profession where profession.name = '" + player.getProfession() + "'", Integer.class);
    }
}
