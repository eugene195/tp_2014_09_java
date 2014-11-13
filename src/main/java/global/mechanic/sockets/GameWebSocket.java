package global.mechanic.sockets;

import global.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene on 10/19/14.
 */
@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, WebSocketService webSocketService) {
        this.myName = myName;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        JSONObject json = new JSONObject(message);

        if (json.has("confirm")) {
            String confirm = json.getString("confirm");
            this.handleConfirm(confirm, json);
        }
        else if (json.has("action")) {
            String action = json.getString("action");
            this.handleAction(action, json);
        }
    }

    private void handleConfirm(String confirm, JSONObject json) {
        if (confirm.equals("loaded")) {

        }
    }

    private void handleAction(String action, JSONObject json) {
        if (action.equals("startGameSession")) {
            setSession(session);

            int playersCnt = json.getInt("playersCnt");
            webSocketService.startGameSession(playersCnt, myName);
        }
        else if (action.equals("addUser")) {
//            int sessionId = json.getInt("sessionId");
            int sessionId = 0;
            webSocketService.addUser(sessionId, this);
        }
        else {
            JSONObject dataJson = json.getJSONObject("data");

            Map<String, Object> data = new HashMap<>();
            for (Object objKey : dataJson.keySet()) {
                String key = (String) objKey;
                data.put(key, dataJson.get(key));
            }

            webSocketService.sendToEngine(action, data, myName);
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
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
