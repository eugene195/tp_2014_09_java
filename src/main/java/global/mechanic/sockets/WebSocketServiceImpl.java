package global.mechanic.sockets;

import global.models.GameSession;
import global.models.Player;
import global.WebSocketService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    public void notifyMyNewScore(Player user) {
        userSockets.get(user.getMyName()).setMyScore(user);
    }

    public void notifyEnemyNewScore(Player user) {
        userSockets.get(user.getMyName()).setEnemyScore(user);
    }

    public void notifyStartGame(Player user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(Player user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
    }

    @Override
    public void sendToClients(String action, Map<String, Object> data, GameSession session) {
        for (String user : session.getPlayers()) {
            GameWebSocket socket = userSockets.get(user);
            socket.sendToClient(action, data);
        }
    }
}
