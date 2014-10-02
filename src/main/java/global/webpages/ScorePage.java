package global.webpages;

import global.MessageSystem;
import global.messages.AbstractMsg;
import global.messages.BestScoresAnswer;
import global.messages.BestScoresQuery;
import global.models.Score;
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
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        PrintWriter printout = response.getWriter();
        JSONObject JObject = new JSONObject();

        this.scores = null;
        this.msys.sendMessage(new BestScoresQuery(), "dbman");
        this.setZombie();

        if (this.scores != null) {
            JObject.put("status", "1");
            JObject.put("bestScores", this.scores);
        }
        else {
            JObject.put("status", "-1");
            JObject.put("message", "There is no session for you");
        }

        response.setContentType("application/json; charset=UTF-8");
        printout.print(JObject);
        printout.flush();
    }

    @Override
    public void finalize(AbstractMsg a_msg) {
        if (a_msg instanceof BestScoresAnswer) {
            BestScoresAnswer msg = (BestScoresAnswer) a_msg;
            this.scores = msg.getScores();
            this.resume();
        }
    }
}
