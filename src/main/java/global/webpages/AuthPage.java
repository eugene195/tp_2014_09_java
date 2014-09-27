package global.webpages;

import global.MessageSystem;
import global.Servlet;
import global.messages.*;
import global.models.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 28.08.2014.
 * The page for a user authentication.
 */
public class AuthPage extends WebPage {
    public static final String URL = "/auth";
    public static final String TML_PATH = "AuthPage.html";

    private final Map<String, HttpSession> httpSessions = new HashMap<>();
    private final Map<String, UserSession> userSessions = new HashMap<>();
    private final MessageSystem msys;

    private UserSession userSession;
    private boolean notValid;

    public AuthPage(MessageSystem msys) {
        this.msys = msys;
        this.notValid = false;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        HttpSession session = request.getSession(false);

        Map<String, Object> context = new LinkedHashMap<>();
        ArrayList<String> errorList = new ArrayList<>();

        if (session != null) {
            context.put("isAuthorized", true);
            context.put("login", session.getAttribute("login").toString());
            errorList.add("You have already been authorized");
        }
        else {
            context.put("isAuthorized", false);
        }

        if (this.notValid) {
            errorList.add("Your data is not valid");
            this.notValid = false;
        }

        context.put("errorList", errorList);

        String page = this.generateHTML(TML_PATH, context);
        response.getWriter().print(page);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(WebPage.CONTENT_TYPE);
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

            response.sendRedirect(ProfilePage.URL);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            this.notValid = true;
            response.sendRedirect(AuthPage.URL);
        }
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
