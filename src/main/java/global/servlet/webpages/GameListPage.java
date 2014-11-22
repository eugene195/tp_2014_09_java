package global.servlet.webpages;

import global.MessageSystem;
import global.mechanic.GameSession;
import global.msgsystem.messages.toServlet.GameSessionsAnswer;
import global.msgsystem.messages.toGameMechanics.GameSessionsQuery;
import global.msgsystem.messages.toServlet.AbstractToServlet;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by max on 13.11.14.
 */
public class GameListPage extends WebPage {
    public static final String URL = "/gameList";
    private final MessageSystem msys;

    private Map<Long, GameSession> sessions;

    public GameListPage(MessageSystem msys) {
        this.sessions = null;
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        this.msys.sendMessage(new GameSessionsQuery(), "gamemech");
        this.setZombie();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();

        JSONObject jObject = new JSONObject();

        if (this.sessions != null) {
            jObject.put("sessions", this.getData());
            jObject.put("status", OK);
        }
        else {
            jObject.put("status", FAILED);
            jObject.put("message", "Internal server error");
        }
        this.sessions = null;

        printout.print(jObject);
        printout.flush();
    }

    @Override
    public void finalizeAsync(AbstractToServlet absMsg) {
        if (absMsg instanceof GameSessionsAnswer) {
            GameSessionsAnswer msg = (GameSessionsAnswer) absMsg;
            this.sessions = msg.getSessions();
        } else {
            System.out.println("Error during GameList.finalizeAsync");
        }
        this.resume();
    }

    private JSONArray getData() {
        JSONArray data = new JSONArray();

        for (Map.Entry<Long, GameSession> entry : this.sessions.entrySet()) {
            JSONObject item = new JSONObject();
            Long sessionId = entry.getKey();
            GameSession session = entry.getValue();

            session.getParams().toJson(item);
            item.put("players", session.getPlayers().toArray());
            item.put("sessionId", (long) sessionId);
            data.put(item);
        }
        return data;
    }
}
