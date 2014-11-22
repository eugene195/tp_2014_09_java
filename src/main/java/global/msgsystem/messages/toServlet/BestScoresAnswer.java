package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.models.Score;
import global.servlet.webpages.ScorePage;

import java.util.ArrayList;

/**
 * Created by max on 02.10.14.
 */
public class BestScoresAnswer extends AbstractToServlet {

    private final ArrayList<Score> scores;

    public BestScoresAnswer(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public ArrayList<Score> getScores() {
        return this.scores;
    }

    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(ScorePage.URL, this);
    }
}
