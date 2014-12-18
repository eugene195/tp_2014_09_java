package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.mechanics.GameSession;
import global.servlet.webpages.GameListPage;

import java.util.Map;

/**
 * Created by max on 13.11.14.
 */
public class GameSessionsAnswer extends AbstractToServlet {
    private final Map<Long, GameSession> sessions;

    public GameSessionsAnswer(Map<Long, GameSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public void exec(Servlet servlet) {
            servlet.transmitToPage(GameListPage.URL, this);
    }

    public Map<Long, GameSession> getSessions() {
        return sessions;
    }
}
