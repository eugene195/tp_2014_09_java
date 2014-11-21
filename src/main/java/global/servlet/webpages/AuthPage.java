package global.servlet.webpages;

import global.AddressService;
import global.MessageSystem;
import global.models.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import java.io.PrintWriter;

import global.msgsystem.messages.toServlet.AbstractToServlet;
import global.msgsystem.messages.toServlet.AuthAnswer;
import global.msgsystem.messages.toDB.AuthQuery;
import global.msgsystem.messages.toServlet.GetOnlineUsersAnswer;
import global.msgsystem.messages.toServlet.GetOnlineUsersQuery;
import org.json.JSONObject;

/**
 * Created by Евгений on 28.08.2014.
 * The page for a user authentication.
 */
public class AuthPage extends WebPage {
    public static final String URL = "/auth";

    private final Map<String, UserSession> userSessions;
    private final MessageSystem msys;

    private UserSession userSession;


    public AuthPage(MessageSystem msys, Map<String, UserSession> userSessions) {
        this.msys = msys;
        this.userSessions = userSessions;
    }


    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");

        if (login == null || passw == null) {
            response.setContentType(WebPage.CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (this.userSessions.containsKey(login)) {
            this.userSessions.remove(login);
        }

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject json = new JSONObject();

        this.userSession = new UserSession(login);

        this.msys.sendMessage(new AuthQuery(this.userSession, passw), "dbman");
        setZombie();

        if (this.userSession.isAuthSuccess()) {
            String userLogin = this.userSession.getLogin();

            HttpSession session = request.getSession();
            session.setAttribute("login", userLogin);
            session.setAttribute("userId", this.userSession.getUserId());
            this.userSessions.put(userLogin, this.userSession);

            json.put("status", "1");
        }
        else {
            json.put("status", "-1");
            json.put("message", "Incorrect login or password");
        }
        printout.print(json);
        printout.flush();
    }

    @Override
    public void finalizeAsync(AbstractToServlet absMsg) {

        if (absMsg instanceof AuthAnswer) {
            AuthAnswer msg = (AuthAnswer) absMsg;
            this.userSession = msg.getUserSession();
            resume();
        }
        else if (absMsg instanceof GetOnlineUsersQuery) {
            this.msys.sendMessage(new GetOnlineUsersAnswer(this.userSessions.keySet()), "servlet");
        }
    }
}
