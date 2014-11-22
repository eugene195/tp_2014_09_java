package global.mechanics.sockets;

import global.SocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import javax.servlet.http.HttpSession;

/**
 * Created by eugene on 10/19/14.
 */
public class SocketCreator implements WebSocketCreator {
    private final SocketService socketService;

    public SocketCreator(SocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        HttpSession session = req.getHttpServletRequest().getSession();
        if (session != null) {
            String login = session.getAttribute("login").toString();

            if (login != null) {
                return new GameSocket(login, socketService);
            }
        }
        return null;
    }
}
