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

    public void notifyMyNewScore(Player user);

    public void notifyEnemyNewScore(Player user);

    public void notifyStartGame(Player user);

    public void notifyGameOver(Player user, boolean win);

    void sendToClients(String action, Map<String, Object> data, GameSession session);
}
