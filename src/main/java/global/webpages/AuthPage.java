package global.webpages;

import global.DataBaseManager;
import global.MessageSystem;
import global.messages.AbstractMsg;
import global.messages.AuthMsg;
import global.messages.CheckedAuthMsg;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

    public AuthPage(MessageSystem msys) {
        super();
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String tml = this.loadPage(TML_PATH);
        response.getWriter().println(tml);
        response.setContentType(WebPage.CONTENT_TYPE);

        HttpSession session = request.getSession(false);
        if(session != null){
//            Error! You've already been authorized
            response.getWriter().println("You have already been authorized");
        }
        else{
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");

        this.msys.sendMessage(new AuthMsg(login, passw), "dbman");
        this.setZombie();

        HttpSession session = request.getSession();

        if (this.successAuth) {
            response.getWriter().println("Success");
            session.setAttribute("user", this.loginAuth);
        }
        else {
            response.getWriter().println("Failure");
            session.invalidate();
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void finalize(AbstractMsg abs_msg) {
        if (abs_msg instanceof CheckedAuthMsg) {
            CheckedAuthMsg msg = (CheckedAuthMsg) abs_msg;
            this.successAuth = msg.isAuthSuccess();
            this.loginAuth = msg.getLogin();
            this.resume();
        }
    }
}
