package global.servlet.webpages;

import global.MessageSystem;
import global.models.UserSession;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Евгений on 31.08.2014.
 */
public class LogoutPage extends WebPage {
    public static final String URL = "/logout";

    private final Map<String, UserSession> userSessions;

    public LogoutPage(MessageSystem msys, Map<String, UserSession> userSessions) {
        this.userSessions = userSessions;
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        PrintWriter printout = response.getWriter();
        JSONObject json = new JSONObject();

        HttpSession session = request.getSession(false);
        if (session != null) {
            String login = session.getAttribute("login").toString();
            session.invalidate();

            if (this.userSessions.containsKey(login)) {
                this.userSessions.remove(login);
            }
            json.put("status", OK);
        }
        else {
            json.put("status", FAILED);
            json.put("message", "There is no session for you");
        }

        response.setContentType("application/json; charset=UTF-8");
        printout.print(json);
        printout.flush();
    }
}
