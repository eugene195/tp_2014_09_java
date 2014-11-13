package global.servlet.webpages;

import global.MessageSystem;
import global.database.dataSets.UsersDataSet;
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
import java.util.HashMap;

/**
 * Created by max on 02.10.14.
 */
public class ScorePage extends WebPage {
    public static final String URL = "/scores";
    private final MessageSystem msys;

    private ArrayList<UsersDataSet> users;

    public ScorePage(MessageSystem msys) {
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        PrintWriter printout = response.getWriter();
        JSONObject JObject = new JSONObject();

        this.users = null;
        this.msys.sendMessage(new BestScoresQuery(), "dbman");
        this.setZombie();

        ArrayList<Score> data = new ArrayList();

        for (UsersDataSet user: users) {
                data.add(new Score(user.getLogin(), user.getScore()));
        }

        if (this.users != null) {
            JObject.put("status", "1");
            JObject.put("bestScores", data);
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
    public void finalizeAsync(AbstractMsg a_msg) {
        if (a_msg instanceof BestScoresAnswer) {
            BestScoresAnswer msg = (BestScoresAnswer) a_msg;
            this.users = msg.getScores();
            this.resume();
        }
    }
}
