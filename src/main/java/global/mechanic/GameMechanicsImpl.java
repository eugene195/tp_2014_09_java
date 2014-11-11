package global.mechanic;

import global.GameMechanics;
import global.engine.Engine;
import global.models.GameSession;
import global.WebSocketService;

import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by eugene on 10/19/14.
 */
public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

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
            startGame(user);
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
        for (Engine engine : this.engines) {
            engine.timerEvent();
        }
    }

    private void startGame(String first) {
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
        this.webSocketService.sendToClients(action, data, session);
    }

    @Override
    public void sendToEngine(String action, Map<String, Object> data, GameSession session) {
        int index = this.allSessions.indexOf(session);
        Engine engine = this.engines.get(index);
        engine.execAction(action, data);
    }
}
