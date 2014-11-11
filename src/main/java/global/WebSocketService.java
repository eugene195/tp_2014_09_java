package global;

import global.models.GameSession;
import global.models.Player;
import global.mechanic.sockets.GameWebSocket;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public interface WebSocketService {
    public void addUser(GameWebSocket user);

    void sendToClients(String action, Map<String, Object> data, GameSession session);
    void sendToEngine(String action, Map<String, Object> data, String myName);

    void notifyStart(GameSession session);
    void notifyEnd(GameSession session);
}
