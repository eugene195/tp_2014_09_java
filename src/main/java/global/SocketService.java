package global;

import global.engine.Params;
import global.mechanics.GameSession;
import global.mechanics.sockets.GameSocket;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public interface SocketService {
    void startGameSession(Params params, GameSocket user);
    void addUser(int sessionId, GameSocket user);

    void sendToClients(String action, Map<String, Object> data, GameSession session);
    void sendToEngine(String action, Map<String, Object> data, String myName);

    void notifyStart(GameSession session);
    void notifyEnd(GameSession session);
    void handleKey(String playerName, String direction);
}
