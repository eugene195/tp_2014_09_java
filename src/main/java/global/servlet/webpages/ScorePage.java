package global.servlet.webpages;

import global.MessageSystem;
import global.models.Score;
import global.msgsystem.messages.AbstractMsg;
import global.msgsystem.messages.BestScoresAnswer;
import global.msgsystem.messages.BestScoresQuery;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by max on 02.10.14.
 */
public class ScorePage extends WebPage {
    public static final String URL = "/scores";
    private final MessageSystem msys;

    private ArrayList<Score> scores;

    public ScorePage(MessageSystem msys) {
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        PrintWriter printout = response.getWriter();
        JSONObject jObject = new JSONObject();

        this.scores = null;
        this.msys.sendMessage(new BestScoresQuery(), "dbman");
        this.setZombie();


        if (this.scores != null) {
            jObject.put("status", OK);
            jObject.put("bestScores", scores);
        }
        else {
            jObject.put("status", FAILED);
            jObject.put("message", "There is no session for you");
        }

        response.setContentType("application/json; charset=UTF-8");
        printout.print(jObject);
        printout.flush();
    }

    @Override
    public void finalizeAsync(AbstractMsg a_msg) {
        if (a_msg instanceof BestScoresAnswer) {
            BestScoresAnswer msg = (BestScoresAnswer) a_msg;
            this.scores = msg.getScores();
            this.resume();
        }
    }
}
