package global.mechanic;

import global.GameMechanics;
import global.engine.Engine;
import global.models.GameSession;
import global.models.Player;
import global.WebSocketService;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by eugene on 10/19/14.
 */
public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private static final int gameTime = 15 * 1000;

    private final WebSocketService webSocketService;
    private final Map<String, GameSession> nameToGame = new HashMap<>();

    private final ArrayList<GameSession> allSessions = new ArrayList<>();
    private final ArrayList<Engine> engines = new ArrayList<>();

    private ArrayList<String> pendingUsers;

    private String waiter;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    public void addUser(String user) {
        if (waiter != null) {
            starGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(STEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.gmStep();
        }
    }

    private void gmStep() {
        int I = 0;
        for (GameSession session : allSessions) {
            Engine engine = engines.get(I++);
            engine.timerEvent();
        }
    }

    private void starGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);

        Engine newEngine = new Engine(this, 100, 100, 1, 2);
        engines.add(newEngine);

        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyGame(gameSession);
    }

    @Override
    public void sendToClients(String action, Map<String, Object> data, Engine from) {
        int index = this.engines.indexOf(from);
        GameSession session = this.allSessions.get(index);

        data.put("snakeId", index);
        this.webSocketService.sendToClients(action, data, session);
    }
}
