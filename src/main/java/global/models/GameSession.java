package global.models;

import global.models.Player;

import java.util.*;

/**
 * Created by eugene on 10/19/14.
 */
public class GameSession {
    private final long startTime;

    private final TreeMap<String, Player> users = new TreeMap<>();

    public GameSession(String user1, String user2) {
        startTime = new Date().getTime();

        Player player1 = new Player(user1);
        player1.setEnemyName(user2);
        users.put(user1, player1);

        Player player2 = new Player(user2);
        player2.setEnemyName(user1);

        users.put(user2, player2);
    }

    public Set<String> getPlayers() {
        return users.descendingKeySet();
    }

    public int getUserId(String user) {
        int id = 0, result = -1;
        for (String key : users.descendingKeySet()) {
            if (key.equals(user)) {
                result = id;
                break;
            }
            id++;
        }
        return result;
    }

    public Player getByName(String user) {
        return users.get(user);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }
}

