package global.servlet.webpages;

import global.AddressService;
import global.MessageSystem;
import global.models.Score;
import global.msgsystem.messages.toServlet.AbstractToServlet;
import global.msgsystem.messages.toServlet.BestScoresAnswer;
import global.msgsystem.messages.toDB.BestScoresQuery;
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
        JSONObject json = new JSONObject();

        this.scores = null;

        this.msys.sendMessage(new BestScoresQuery(), "dbman");
        setZombie();

        if (this.scores != null) {
            json.put("status", "1");
            json.put("bestScores", scores);
        }
        else {
            json.put("status", "-1");
            json.put("message", "There is no session for you");
        }

        response.setContentType("application/json; charset=UTF-8");
        printout.print(json);
        printout.flush();
    }

    @Override
    public void finalizeAsync(AbstractToServlet absMsg) {
        if (absMsg instanceof BestScoresAnswer) {
            BestScoresAnswer msg = (BestScoresAnswer) absMsg;
            this.scores = msg.getScores();
            resume();
        }
    }
}
