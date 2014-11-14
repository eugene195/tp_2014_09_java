package global.msgsystem.messages;

import global.Servlet;
import global.mechanic.GameSession;
import global.servlet.webpages.GameListPage;

import java.util.Map;

/**
 * Created by max on 13.11.14.
 */
public class GameSessionsAnswer extends AbstractMsg {
    private final Map<Long, GameSession> sessions;

    public GameSessionsAnswer(Map<Long, GameSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public void exec(Runnable abonent) {
        if (abonent instanceof Servlet) {
            Servlet servlet = (Servlet) abonent;
            servlet.transmitToPage(GameListPage.URL, this);
        } else {
            System.out.println("Error during GameSessionsAnswer");
        }
    }

    public Map<Long, GameSession> getSessions() {
        return this.sessions;
    }
}
