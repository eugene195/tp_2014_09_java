package global.messages;

import global.Servlet;
import global.models.Score;
import global.webpages.ScorePage;

import java.util.ArrayList;

/**
 * Created by max on 02.10.14.
 */
public class BestScoresAnswer extends AbstractMsg {

    private final ArrayList<Score> scores;

    public BestScoresAnswer(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public ArrayList<Score> getScores() {
        return this.scores;
    }

    @Override
    public void exec(Runnable runnable) {
        if (runnable instanceof Servlet) {
            Servlet servlet = (Servlet) runnable;
            servlet.transmitToPage(ScorePage.URL, this);
        }
    }
}
