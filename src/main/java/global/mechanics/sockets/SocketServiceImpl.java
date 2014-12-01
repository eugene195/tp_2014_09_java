package global.mechanics.sockets;

import global.GameMechanics;
import global.engine.Params;
import global.mechanics.GameSession;
import global.SocketService;

import java.util.ArrayList;
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
        int userId = 0;
        ArrayList<String> users = new ArrayList<>();
        for (String user : session.getPlayers()) {
            users.add(user);
        }

        for (String user : session.getPlayers()) {
            GameSocket socket = userSockets.get(user);
            data.put("snakeId", userId);
            data.put("names", users);
            socket.sendToClient(action, data);
            userId++;
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
        for (String user : gameSession.getPlayers()) {
            nameToGame.put(user, gameSession);
        }

        for (String user : gameSession.getPlayers()) {
            GameSocket socket = userSockets.get(user);
            socket.sendToClient("notifyStart");
        }
    }

    @Override
    public void notifyEnd(GameSession gameSession) {
        gameSession.getPlayers().forEach(this.nameToGame::remove);
        gameSession.getPlayers().forEach(this.userSockets::remove);

        for (String user : gameSession.getPlayers()) {
            GameSocket socket = userSockets.get(user);
            socket.sendToClient("notifyEnd");
        }
    }

    @Override
    public void handleKey(String playerName, String direction) {

    }
}
