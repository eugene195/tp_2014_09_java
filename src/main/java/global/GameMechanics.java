package global;

import global.engine.Engine;
import global.models.GameSession;

import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public interface GameMechanics extends Runnable {
    public void addUser(String user);

    public void run();

    WebSocketService getWebSocketService();

    void sendToClients(String action, Map<String, Object> data, Engine from);
    void sendToEngine(String action, Map<String, Object> data, GameSession session);

    void startGame(String first,String second);
    void endGame(Engine endine);
}
