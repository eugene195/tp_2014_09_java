package global.msgsystem.messages;

import global.Servlet;
import global.mechanic.GameSession;
import global.servlet.webpages.GameListPage;

import java.util.Collection;

/**
 * Created by max on 13.11.14.
 */
public class GameSessionsAnswer extends AbstractMsg {
    private final Collection<GameSession> sessions;

    public GameSessionsAnswer(Collection<GameSession> sessions) {
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

    public Collection<GameSession> getSessions() {
        return this.sessions;
    }
}
