package global.mechanic;

import global.models.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by eugene on 10/19/14.
 */
public class GameSession {
    private static AtomicLong idCounter = new AtomicLong();

    private final long startTime;
    private final int playersCnt;

    private final Map<String, Player> players = new HashMap<>();

    public GameSession(int playersCnt, String name) {
        startTime = new Date().getTime();
        this.playersCnt = playersCnt;

        long id = idCounter.getAndIncrement();
        players.put(name, new Player(name, id));
    }

    public GameSession(String user1, String user2) {
        this(2, user1);
        long id = idCounter.getAndIncrement();
        players.put(user2, new Player(user2, id));
    }

    /**
     * Add new player to game session
     * @param name
     * @return Is a gameSession fully filled
     */
    public boolean add(String name) {
        long id = idCounter.getAndIncrement();
        Player player = new Player(name, id);
        players.put(name, player);

        if (players.size() == playersCnt) {
            return true;
        }
        return false;
    }

    public int getPlayersCnt() {
        return this.playersCnt;
    }

    public Set<String> getPlayers() {
        return players.keySet();
    }

    public long getSnakeId(String name) {
        return players.get(name).getSnake();
    }

    public Player getByName(String name) {
        return players.get(name);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }
}

