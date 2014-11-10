package global.msgsystem.messages;

import global.Servlet;
import global.database.dataSets.UsersDataSet;
import global.servlet.webpages.ScorePage;

import java.util.ArrayList;

/**
 * Created by max on 02.10.14.
 */
public class BestScoresAnswer extends AbstractMsg {

    private final ArrayList<UsersDataSet> users;

    public BestScoresAnswer(ArrayList<UsersDataSet> users) {
        this.users = users;
    }

    public ArrayList<UsersDataSet> getScores() {
        return this.users;
    }

    @Override
    public void exec(Runnable runnable) {
        if (runnable instanceof Servlet) {
            Servlet servletImpl = (Servlet) runnable;
            servletImpl.transmitToPage(ScorePage.URL, this);
        }
    }
}
