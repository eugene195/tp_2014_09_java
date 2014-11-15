package global;

import global.engine.Params;
import global.mechanic.GameSession;
import global.mechanic.sockets.GameWebSocket;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public interface WebSocketService {
    void startGameSession(Params params, GameWebSocket user);
    void addUser(int sessionId, GameWebSocket user);

    void sendToClients(String action, Map<String, Object> data, GameSession session);
    void sendToEngine(String action, Map<String, Object> data, String myName);

    void notifyStart(GameSession session);
    void notifyEnd(GameSession session);
    void handleKey(String playerName, String direction);
}
