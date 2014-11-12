package global.mechanic;

import global.models.Player;

import java.util.*;

/**
 * Created by eugene on 10/19/14.
 */
public class GameSession {
    private final long startTime;
    private final int playersCnt;

    private final Map<String, Player> players = new HashMap<>();

    public GameSession(int playersCnt) {
        startTime = new Date().getTime();
        this.playersCnt = playersCnt;
    }

    public GameSession(String user1, String user2) {
        this(2);
        players.put(user1, new Player(user1, 0));
        players.put(user2, new Player(user2, 1));
    }

    /**
     * Add new player to game session
     * @param name
     * @return Is a gameSession fully filled
     */
    public boolean add(String name) {
        int newId = players.size();
        Player player = new Player(name, newId);
        players.put(name, player);

        if (players.size() == playersCnt) {
            return true;
        }
        return false;
    }

    public Set<String> getPlayers() {
        return players.keySet();
    }

    public int getSnakeId(String name) {
        return players.get(name).getSnake();
    }

    public Player getByName(String name) {
        return players.get(name);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }
}

