package global.mechanic.sockets;

import global.GameMechanics;
import global.mechanic.GameSession;
import global.WebSocketService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public class WebSocketServiceImpl implements WebSocketService {

    private final Map<String, GameWebSocket> userSockets = new HashMap<>();
    private final Map<String, GameSession> nameToGame = new HashMap<>();

    private final GameMechanics mechanics;

    public WebSocketServiceImpl(GameMechanics mechanics) {
        this.mechanics = mechanics;
    }

    public void startGameSession(int playersCnt, String myName) {
        this.mechanics.startGameSession(playersCnt, myName);
    }

    @Override
    public void addUser(int sessionId, GameWebSocket user) {
        String myName = user.getMyName();
        userSockets.put(myName, user);
        mechanics.addToSession(sessionId, myName);
    }

    @Override
    public void sendToClients(String action, Map<String, Object> data, GameSession session) {
        int userId = 0;
        for (String user : session.getPlayers()) {
            GameWebSocket socket = userSockets.get(user);
            data.put("snakeId", userId);
            socket.sendToClient(action, data);
            userId++;
        }
    }

    @Override
    public void sendToEngine(String action, Map<String, Object> data, String myName) {
        GameSession session = this.nameToGame.get(myName);
        data.put("snakeId", session.getSnakeId(myName));
        mechanics.sendToEngine(action, data, session);
    }

    @Override
    public void notifyStart(GameSession gameSession) {
        for (String user : gameSession.getPlayers()) {
            nameToGame.put(user, gameSession);
        }

        for (String user : gameSession.getPlayers()) {
            GameWebSocket socket = userSockets.get(user);
            socket.sendToClient("notifyStart");
        }
    }

    @Override
    public void notifyEnd(GameSession gameSession) {
        for (String user : gameSession.getPlayers()) {
            this.nameToGame.remove(user);
        }

        for (String user : gameSession.getPlayers()) {
            GameWebSocket socket = userSockets.get(user);
            socket.sendToClient("notifyEnd");
        }
    }
}
