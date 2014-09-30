package global.webpages;

import global.MessageSystem;
import global.Servlet;
import global.messages.*;
import global.models.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.PrintWriter;
import org.json.JSONObject;

/**
 * Created by Евгений on 28.08.2014.
 * The page for a user authentication.
 */
public class AuthPage extends WebPage {
    public static final String URL = "/auth";

    private final Map<String, HttpSession> httpSessions = new HashMap<>();
    private final Map<String, UserSession> userSessions = new HashMap<>();
    private final MessageSystem msys;


    private UserSession userSession;
    private boolean notValid;


    public AuthPage(MessageSystem msys) {
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {

    }

    private void cleanSessions(final String login) {
        if (this.httpSessions.containsKey(login)) {
            this.httpSessions.get(login).invalidate();
            this.httpSessions.remove(login);
        }

        if (this.userSessions.containsKey(login)) {
            this.userSessions.remove(login);
        }
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");

        this.cleanSessions(login);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject JObject = new JSONObject();

        this.userSession = new UserSession(login);
        this.msys.sendMessage(new AuthQuery(this.userSession, passw), "dbman");
        this.setZombie();

        if (this.userSession.isAuthSuccess()) {
            String userLogin = this.userSession.getLogin();

            HttpSession session = request.getSession();
            session.setAttribute("login", userLogin);
            session.setAttribute("userId", this.userSession.getUserId());
            this.httpSessions.put(userLogin, session);
            this.userSessions.put(userLogin, this.userSession);

            JObject.put("status", "1");
        }
        else {
            JObject.put("status", "-1");
            JObject.put("message", "Incorrect login or password");
        }
        printout.print(JObject);
        printout.flush();
    }

    @Override
    public void finalize(AbstractMsg abs_msg) {

        if (abs_msg instanceof AuthAnswer) {
            AuthAnswer msg = (AuthAnswer) abs_msg;
            this.userSession = msg.getUserSession();
            this.resume();
        }
        else if (abs_msg instanceof LogoutMsg) {
            LogoutMsg msg = (LogoutMsg) abs_msg;
            this.cleanSessions(msg.getLogin());
            // System.out.println("ura: " + msg.getLogin());
        }
        else if (abs_msg instanceof GetOnlineUsersQuery) {
            this.msys.sendMessage(new GetOnlineUsersAnswer(this.userSessions.keySet()), "servlet");
        }
    }
}
