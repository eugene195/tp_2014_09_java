package global;

import global.engine.Engine;
import global.mechanic.GameSession;

import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public interface GameMechanics extends Runnable {

    void run();

    WebSocketService getWebSocketService();

    void sendToClients(String action, Map<String, Object> data, Engine from);
    void sendToEngine(String action, Map<String, Object> data, GameSession session);

    void startGameSession(int playersCnt);
    void addToSession(long sessionId, String player);

    void startGame(GameSession gameSession);
    void endGame(Engine endine);
}
