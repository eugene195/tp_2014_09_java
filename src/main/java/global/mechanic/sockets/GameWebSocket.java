package global.mechanic.sockets;

import global.GameMechanics;
import global.models.Player;
import global.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        JSONObject message = new JSONObject(data);
        String action = message.getString("action");

        if (action.equals("addUser")) {
            this.addUser();
        }
        else {
             // BY PROTOCOL
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {

    }

    public void addUser() {
        setSession(session);
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    public void sendToClient(String action, Map<String, Object> data) {
        JSONObject json = new JSONObject();
        json.put("action", action);
        json.put("status", "OK");
        json.put("data", data);

        try {
            session.getRemote().sendString(json.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void sendToClient(String action) {
        JSONObject json = new JSONObject();
        json.put("action", action);
        json.put("status", "OK");

        try {
            session.getRemote().sendString(json.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }
}
