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

    @Override
    public void sendToClients(String action, Map<String, Object> data, GameSession session) {
        for (String user : session.getPlayers()) {
            GameWebSocket socket = userSockets.get(user);
            socket.sendToClient(action, data);
        }
    }

    @Override
    public void notifyGame(GameSession session) {
        for (String user : session.getPlayers()) {
            GameWebSocket socket = userSockets.get(user);
            socket.sendToClient("notifyGame");
        }
    }
}
