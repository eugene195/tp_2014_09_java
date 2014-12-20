package global;

import global.engine.Engine;
import global.engine.Params;
import global.mechanics.GameSession;

import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public interface GameMechanics extends Runnable, Abonent {

    void run();

    SocketService getSocketService();

    void sendToClients(String action, Map<String, Object> data, Engine from);
    void sendToEngine(String action, Map<String, Object> data, GameSession session);

    void startGameSession(Params params, String player);
    void addToSession(long sessionId, String player);

    void startGame(GameSession gameSession);
    void endGame(Engine endine, Long winnerSnakeId);

    void getGameSessions(String addressTo);
}
