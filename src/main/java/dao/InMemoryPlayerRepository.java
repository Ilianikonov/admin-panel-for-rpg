package dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map <Long, Player> players = new HashMap<>();

    @Override
    public Player createPlayer(Player player) {
        long id = players.size();
        player.setId(id);
        players.put(id, player);
        return player;
    }

    @Override
    public Player updatePlayer(Player player) {

        return null;
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
        return null;
    }

    @Override
    public int getPlayersCount(PlayerFilter playerFilter) {
        return 0;
    }
}
