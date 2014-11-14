package global.mechanic;

import global.GameMechanics;
import global.engine.Engine;
import global.mechanic.sockets.WebSocketServiceImpl;
import global.WebSocketService;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * Created by eugene on 10/19/14.
 */
public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;
    private static AtomicLong idCounter = new AtomicLong();

    private final WebSocketService webSocketService;

    private final Map<Long, GameSession> waitingPlayers = new HashMap<>();
    private final ArrayList<GameSession> playing = new ArrayList<>();
    private final ArrayList<Engine> engines = new ArrayList<>();

    public GameMechanicsImpl() {
        this.webSocketService = new WebSocketServiceImpl(this);
    }

    @Override
    public WebSocketService getWebSocketService() {
        return webSocketService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(STEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            this.gmStep();
        }
    }

    private void gmStep() {
        for (Engine engine : this.engines) {
            engine.timerEvent();
        }
    }


    @Override
    public void startGameSession(int playersCnt) {
        GameSession gameSession = new GameSession(playersCnt);
        long id = idCounter.getAndIncrement();
        this.waitingPlayers.put(id, gameSession);
    }

    @Override
    public void addToSession(long sessionId, String player) {

        // TODO delete ifs

        GameSession gameSession = this.waitingPlayers.get(sessionId);

//        <--
        if (gameSession == null)
            startGameSession(2);
        gameSession = this.waitingPlayers.get(sessionId);
//        -->

        boolean filled = gameSession.add(player);

        if (filled) {
            this.waitingPlayers.remove(sessionId);
            this.startGame(gameSession);
        }
    }

    @Override
    public void startGame(GameSession gameSession) {
        this.playing.add(gameSession);
        this.webSocketService.notifyStart(gameSession);

        Engine newEngine = new Engine(this, 100, 100, 1, 2);
        this.engines.add(newEngine);
        // TODO: after all confirms
        newEngine.launch();
    }

    @Override
    public void endGame(Engine engine) {
        int index = this.engines.indexOf(engine);

        if (index != -1) {
            this.engines.remove(index);
            GameSession gameSession = playing.get(index);
            this.webSocketService.notifyEnd(gameSession);
            this.playing.remove(gameSession);
        } else {
            System.out.println("endGame index error");
        }
    }

    @Override
    public void sendToClients(String action, Map<String, Object> data, Engine from) {
        int index = this.engines.indexOf(from);

        if (index != -1) {
            GameSession session = this.playing.get(index);
            this.webSocketService.sendToClients(action, data, session);
        } else {
            System.out.println("sendToClients index error");
        }
    }

    @Override
    public void sendToEngine(String action, Map<String, Object> data, GameSession session) {
        int index = this.playing.indexOf(session);

        if (index != -1) {
            Engine engine = this.engines.get(index);
            engine.execAction(action, data);
        } else {
            System.out.println("sendToEngine index error");
        }
    }
}
