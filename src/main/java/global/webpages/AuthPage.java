package global.webpages;

import global.MessageSystem;
import global.messages.AbstractMsg;
import global.messages.AuthQuery;
import global.messages.AuthAnswer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 28.08.2014.
 * The page for a user authentication.
 */
public class AuthPage extends WebPage {
    public static final String URL = "/auth";
    public static final String TML_PATH = "AuthPage.html";

    private final MessageSystem msys;

    private boolean successAuth;
    private String loginAuth;
    private Long userId;

    private static final String PAGE_VML = "AuthPage.html";

    public AuthPage(MessageSystem msys) {
        super();
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        HttpSession session = request.getSession(false);

        Map<String, Object> context = new LinkedHashMap<>();
        ArrayList<String> errorList = new ArrayList<>();

        if(session != null){
            context.put("isAuthorized", true);
            context.put("login", session.getAttribute("login").toString());
            errorList.add("You have already been authorized");
        }
        else{
            context.put("isAuthorized", "false");
        }
        context.put("errorList", errorList);
        String page = this.generateHTML(PAGE_VML, context);
        response.getWriter().print(page);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");
        Map<String, Object> context = new LinkedHashMap<>();
        ArrayList<String> messageList = new ArrayList<>();

        this.msys.sendMessage(new AuthQuery(login, passw), "dbman");
        this.setZombie();

        HttpSession session = request.getSession();

        if (this.successAuth) {
            session.setAttribute("login", this.loginAuth);
            session.setAttribute("userId", this.userId);
            response.sendRedirect(ProfilePage.URL);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            messageList.add("Authorisation Failed, login or password incorrect");
            context.put("isAuthorized", "false");
            session.invalidate();
            response.sendRedirect(AuthPage.URL);
        }
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
