package global.mechanics;

import global.engine.Params;
import global.models.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by eugene on 10/19/14.
 */
public class GameSession {
    private static AtomicLong idCounter = new AtomicLong();

    private final long startTime;
    private Params params;

    private final Map<String, Player> players = new HashMap<>();

    public GameSession(Params params, String name) {
        startTime = new Date().getTime();
        this.params = params;

        long id = idCounter.getAndIncrement();
        players.put(name, new Player(name, id));
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
        return (players.size() == params.playersCnt);
    }

    public Params getParams() { return this.params; }

    public int getPlayersCnt() {
        return this.params.playersCnt;
    }

    public Set<String> getPlayerNames() {
        return players.keySet();
    }

    public Collection<Player> getPlayers() {
        return players.values();
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

