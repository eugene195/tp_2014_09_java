package global.mechanics.sockets;

import global.SocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by eugene on 10/19/14.
 */
@WebServlet(name = "SocketServlet", urlPatterns = {"/gameplay"})
public class SocketServlet extends WebSocketServlet {
    private static final int IDLE_TIME = 120 * 1000;
    private final SocketService socketService;

    public SocketServlet(SocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new SocketCreator(socketService));
    }
}