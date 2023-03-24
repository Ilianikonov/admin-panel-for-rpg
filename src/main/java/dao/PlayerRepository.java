package dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.PlayerFilter;

import java.util.List;

public interface PlayerRepository {
    Player createPlayer(Player player);
    Player updatePlayer(Player player);
    Player deletePlayer(long id);
    Player getPlayerById(long id);
    List <Player> getPlayers(PlayerFilter playerFilter);
    int getPlayersCount(PlayerFilter playerFilter);
}
