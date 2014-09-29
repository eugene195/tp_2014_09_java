package global.webpages;

import global.MessageSystem;
import global.messages.AbstractMsg;
import global.messages.AuthQuery;
import global.messages.AuthAnswer;

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

    private final Map<String, HttpSession> authSessions = new HashMap<>();
    private final MessageSystem msys;

    private boolean successAuth;
    private String loginAuth;
    private Long userId;

    public AuthPage(MessageSystem msys) {
        super();
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {

    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");

        if (this.authSessions.containsKey(login)) {
            this.authSessions.get(login).invalidate();
            this.authSessions.remove(login);
        }

        this.msys.sendMessage(new AuthQuery(login, passw), "dbman");
        this.setZombie();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject JObject = new JSONObject();

        if (this.successAuth) {
            HttpSession session = request.getSession();
            session.setAttribute("login", this.loginAuth);
            session.setAttribute("userId", this.userId);
            this.authSessions.put(this.loginAuth, session);
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

            this.successAuth = msg.isAuthSuccess();
            this.loginAuth = msg.getLogin();
            this.userId = msg.getUserId();

            this.resume();
        }
    }
}
