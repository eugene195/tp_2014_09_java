package global.mechanics;

import global.GameMechanics;
import global.MessageSystem;
import global.engine.Engine;
import global.engine.Params;
import global.mechanics.sockets.SocketServiceImpl;
import global.SocketService;
import global.msgsystem.messages.toServlet.GameSessionsAnswer;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * Created by eugene on 10/19/14.
 */
public class GameMechanicsImpl implements GameMechanics {
    private static final String MECHANIC_ADDRESS = "gamemech";
    private static final int STEP_TIME = 30;
    private static AtomicLong idCounter = new AtomicLong();

    private final MessageSystem msys;
    private final SocketService socketService;

    private final Map<Long, GameSession> waitingPlayers = new HashMap<>();
    private final ArrayList<GameSession> playing = new ArrayList<>();
    private final ArrayList<Engine> engines = new ArrayList<>();

    public GameMechanicsImpl(MessageSystem msys) {
        this.msys = msys;
        this.msys.register(this, MECHANIC_ADDRESS);

        this.socketService = new SocketServiceImpl(this);
    }

    @Override
    public SocketService getSocketService() {
        return socketService;
    }

    @Override
    public void run() {
        while (true) {
            this.msys.executeFor(this);
            try {
                sleep(STEP_TIME);
                this.gmStep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void gmStep() {
        for (Engine engine : this.engines) {
            engine.timerEvent();
        }
    }

    /**
     * Checking if the player is already in one of the waiting sessions
     * @param player
     * @return can user create a new session?
     */
    private boolean checkAlready(String player) {
        for (GameSession gameSession : this.waitingPlayers.values())
            if (gameSession.getPlayers().contains(player)) {
                return false;
            }

        return true;
    }

    @Override
    public void startGameSession(Params params, String player) {
        if (this.checkAlready(player)) {
            GameSession gameSession = new GameSession(params, player);
            long id = idCounter.getAndIncrement();
            this.waitingPlayers.put(id, gameSession);
        }
        else {
            System.out.println("Player already has a waiting session " + player);
        }
    }

    @Override
    public void addToSession(long sessionId, String player) {
        GameSession gameSession = this.waitingPlayers.get(sessionId);

        if (gameSession == null) {
            System.out.println("Wrong sessionId during addToSession: " + sessionId);
            return;
        }

        if (this.checkAlready(player)) {
            boolean filled = gameSession.add(player);
            if (filled) {
                this.waitingPlayers.remove(sessionId);
                this.startGame(gameSession);
                this.waitingPlayers.remove(sessionId);
            }
        }
        else {
            System.out.println("Player already has a waiting session " + player);
        }
    }

    @Override
    public void startGame(GameSession gameSession) {
        this.playing.add(gameSession);
        this.socketService.notifyStart(gameSession);

        Params params = gameSession.getParams();
        Engine newEngine = new Engine(msys, this, params.width, params.height, params.speed);
        newEngine.generateSnakes(gameSession);
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
            this.socketService.notifyEnd(gameSession);
            this.playing.remove(index);
        } else {
            System.out.println("endGame index error");
        }
    }

    @Override
    public void sendToClients(String action, Map<String, Object> data, Engine from) {
        int index = this.engines.indexOf(from);

        if (index != -1) {
            GameSession session = this.playing.get(index);
            this.socketService.sendToClients(action, data, session);
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

    @Override
    public void getGameSessions() {
        GameSessionsAnswer msg = new GameSessionsAnswer(this.waitingPlayers);
        this.msys.sendMessage(msg, "servlet");
    }
}