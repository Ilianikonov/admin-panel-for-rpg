package com.example.demo.dao.database;

import com.example.demo.dao.ComparatorPlayer;
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
                "inner join profession on player.profession_id = profession.id\n" +
                getFilterPlayerSQL(playerFilter), new PlayerMapper()).stream()
                .sorted(new ComparatorPlayer(playerFilter.getPlayerOrder()))
                .skip(playerFilter.getPageSize() * playerFilter.getPageNumber())
                .limit(playerFilter.getPageSize())
                .toList();
    }
    private String getFilterPlayerSQL(PlayerFilter playerFilter){
        String filterPlayer = "";
        if (playerFilter.getName() != null) {
            filterPlayer += "player.name = '" + playerFilter.getName() + "'\n";
        }
        if (playerFilter.getTitle() != null) {
            filterPlayer += "player.title = '" + playerFilter.getTitle() + "'\n";
        }
        if (playerFilter.getRace() != null) {
            filterPlayer += "race.name = '" + playerFilter.getRace() + "'\n";
        }
        if (playerFilter.getProfession() != null) {
            filterPlayer += "profession.name = '" + playerFilter.getProfession() + "'\n";
        }
        if (playerFilter.getAfter() != null) {
            filterPlayer += "player.birthday <= '" + playerFilter.getAfter() + "'\n";
        }
        if (playerFilter.getBefore() != null) {
            filterPlayer += "player.birthday >= '" + playerFilter.getBefore() + "'\n";
        }
        if (playerFilter.getBanned() != null) {
            filterPlayer += "player.banned = '" + playerFilter.getBanned() + "'\n";
        }
        if (playerFilter.getMinExperience() != null) {
            filterPlayer += "player.experience <= '" + playerFilter.getMinExperience() + "'\n";
        }
        if (playerFilter.getMaxExperience() != null) {
            filterPlayer += "player.experience >= '" + playerFilter.getMaxExperience() + "'\n";
        }
        if (playerFilter.getMinLevel() != null) {
            filterPlayer += "player.level <= '" + playerFilter.getMinLevel() + "'\n";
        }
        if (playerFilter.getMaxLevel() != null) {
            filterPlayer += "player.level >= '" + playerFilter.getMaxLevel() + "'\n";
        }
        System.out.println(filterPlayer);
        return filterPlayer += ";";
    }

    @Override
    public int getPlayersCount(PlayerFilter playerFilter) {
        return jdbcTemplate.query("select * from player\n" +
                "inner join race on player.race_id = race.id\n" +
                "inner join profession on player.profession_id = profession.id\n" +
                getFilterPlayerSQL(playerFilter), new PlayerMapper()).size();
    }
    private int playerGetRaceId(Player player){
       return jdbcTemplate.queryForRowSet("select id from race where race.name = ?", new Object[]{player.getRace()}).getRow();
    }
    private int playerGetProfessionId(Player player){

            return jdbcTemplate.queryForRowSet("select id from profession where profession.name = ?", new Object[]{player.getRace()}).getRow();
    }
}
