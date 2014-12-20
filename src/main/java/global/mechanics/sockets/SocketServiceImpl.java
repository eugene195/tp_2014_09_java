package global.mechanics.sockets;

import global.GameMechanics;
import global.engine.Params;
import global.mechanics.GameSession;
import global.SocketService;
import global.models.Player;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
public class SocketServiceImpl implements SocketService {

    private final Map<String, GameSocket> userSockets = new HashMap<>();
    private final Map<String, GameSession> nameToGame = new HashMap<>();

    private final GameMechanics mechanics;

    public SocketServiceImpl(GameMechanics mechanics) {
        this.mechanics = mechanics;
    }

    @Override
    public void startGameSession(Params params, GameSocket user) {
        String myName = user.getMyName();

        if (! nameToGame.containsKey(myName)) {
            userSockets.put(myName, user);
            this.mechanics.startGameSession(params, myName);
        }
        else {
            System.out.println("Repeated attempt to start game from user: " + myName);
        }
    }

    @Override
    public void addUser(int sessionId, GameSocket user) {
        String myName = user.getMyName();

        if (! nameToGame.containsKey(myName)) {
            userSockets.put(myName, user);
            this.mechanics.addToSession(sessionId, myName);
        }
        else {
            System.out.println("Repeated attempt to add to game from user: " + myName);
        }
    }

    @Override
    public void sendToClients(String action, Map<String, Object> data, GameSession session) {
        Set<String> names = session.getPlayerNames();

        for (Player player : session.getPlayers()) {
            GameSocket socket = userSockets.get(player.getName());
            data.put("snakeId", player.getSnake());
            data.put("names", names);
            socket.sendToClient(action, data);
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
        for (String user : gameSession.getPlayerNames()) {
            nameToGame.put(user, gameSession);
        }

        for (String user : gameSession.getPlayerNames()) {
            GameSocket socket = userSockets.get(user);
            socket.sendToClient("notifyStart");
        }
    }

    @Override
    public void notifyEnd(GameSession gameSession) {
        for (String user : gameSession.getPlayerNames()) {
            GameSocket socket = userSockets.get(user);
            socket.sendToClient("notifyEnd");
        }
        gameSession.getPlayerNames().forEach(this.nameToGame::remove);
        gameSession.getPlayerNames().forEach(this.userSockets::remove);
    }

    @Override
    public void handleKey(String playerName, String direction) {

    }
}
