package global.models;

import global.models.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public class GameSession {
    private final long startTime;
    private Player first;
    private Player second;

    private ArrayList<String> players;

    private Map<String, Player> users = new HashMap<>();

    public GameSession(String user1, String user2) {
        startTime = new Date().getTime();
        Player player1 = new Player(user1);
        player1.setEnemyName(user2);

        Player player2 = new Player(user2);
        player2.setEnemyName(user1);

        users.put(user1, player1);
        users.put(user2, player2);

        this.first = player1;
        this.second = player2;
    }

    public Player getEnemy(String user) {
        String enemyName = users.get(user).getEnemyName();
        return users.get(enemyName);
    }

    public Player getSelf(String user) {
        return users.get(user);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public Player getFirst() {
        return first;
    }

    public Player getSecond() {
        return second;
    }

    public  boolean isFirstWin(){
        return first.getMyScore() > second.getMyScore();
    }
}

