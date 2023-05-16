package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@ConditionalOnProperty(name = "application.repository.type", havingValue = "database")
public class DataBasePlayerRepository implements PlayerRepository{
    private final JdbcTemplate jdbcTemplate;

    public DataBasePlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void clear() {
        jdbcTemplate.update("");
    }

    @Override
    public Player createPlayer(Player player) {
//        String sql = "insert into player (name,title,race_id,profession_id,experience,level,until_next_level,birthday,baned) values ("
//                +"'"+ player.getName()+"', "
//                +"'"+ player.getTitle()+"', "
//                +"'"+ playerGetRaceId(player)+"', "
//                +"'"+ playerGetProfessionId(player)+"', "
//                +"'"+ player.getExperience()+"', "
//                +"'"+ player.getLevel()+"', "
//                +"'"+ player.getUntilNextLevel()+"', "
//                +"'"+ player.getBirthday()+"', "
//                +"'"+ player.getBanned()+"')"
//                +"RETURNING id";
       player.setId((long) jdbcTemplate.update("INSERT INTO player VALUES (?,?,?,?,?,?,?,?,?) RETURNING id",
                player.getName(),player.getTitle(),playerGetRaceId(player),
                playerGetProfessionId(player),player.getExperience(),player.getLevel(),
                player.getUntilNextLevel(),player.getBirthday(),player.getBanned()));
        return jdbcTemplate.query("SELECT * FROM player WHERE id = ?", new Object[]{player.getId()},
                new PlayerMapper()).stream().findAny().orElse(null);
    }

    @Override
    public Player updatePlayer(Player player) {
        jdbcTemplate.update("UPDATE player SET name=?, title=?, race_id=?, profession_id=?, experience=?, level=?, until_next_level=?, birthday=?, baned=? WHERE id = ?",
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
        return jdbcTemplate.queryForObject("SELECT * FROM player WHERE id = ?", new Object[]{id},
                new PlayerMapper());
    }

    @Override
    public List<Player> getPlayers(PlayerFilter playerFilter) {
        return jdbcTemplate.query("select player.id, player.name player_name, title, race.name race_name, profession.name profession_name, experience, level, until_next_level, birthday, banned\n" +
                        "from player \n" +
                        "inner join race on player.race_id = race.id\n" +
                        "inner join profession on player.profession_id = profession.id", new PlayerMapper()).stream()
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
                .sorted(new ComparatorPlayer(playerFilter.getPlayerOrder()))
                .skip(playerFilter.getPageSize() * playerFilter.getPageNumber())
                .limit(playerFilter.getPageSize())
                .toList();
    }

    @Override
    public int getPlayersCount(PlayerFilter playerFilter) {
        return getPlayers(playerFilter).size();
    }
    private int playerGetRaceId(Player player){
        if   (player.getRace() == Race.HUMAN){
            return 1;
        } else if (player.getRace() == Race.DWARF){
            return 2;
        } else if (player.getRace() == Race.ELF){
            return 3;
        } else  if (player.getRace() == Race.GIANT){
            return 4;
        } else if (player.getRace() == Race.ORC){
            return 5;
        } else if (player.getRace() == Race.TROLL){
            return 6;
        } else {
            return 7;
        }
    }
    private int playerGetProfessionId(Player player){
        if   (player.getProfession() == Profession.WARRIOR){
            return 1;
        } else if (player.getProfession() == Profession.ROGUE){
            return 2;
        } else if (player.getProfession() == Profession.SORCERER){
            return 3;
        } else  if (player.getProfession() == Profession.CLERIC){
            return 4;
        } else if (player.getProfession() == Profession.PALADIN){
            return 5;
        } else if (player.getProfession() == Profession.NAZGUL){
            return 6;
        } else if (player.getProfession() == Profession.WARLOCK) {
            return 7;
        } else {
            return 8;
        }
    }
}
