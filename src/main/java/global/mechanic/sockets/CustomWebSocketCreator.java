package global.mechanic.sockets;

import global.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import javax.servlet.http.HttpSession;

/**
 * Created by eugene on 10/19/14.
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private WebSocketService webSocketService;

    public CustomWebSocketCreator(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        HttpSession session = req.getHttpServletRequest().getSession();
        if (session != null) {
            String login = session.getAttribute("login").toString();
            return new GameWebSocket(login, webSocketService);
        }
        else
            return null;
    }
}
