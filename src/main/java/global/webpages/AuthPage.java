package global.webpages;

import global.MessageSystem;
import global.messages.CheckedAuthMsg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Евгений on 28.08.2014.
 * The page for a user authentication.
 */
public class AuthPage extends WebPage {

    private final MessageSystem msys;

    public void finalizeAuth (CheckedAuthMsg msg) {
        if(msg.isAuthSuccess()) {
            // Создать сессию, записать имя User'a
        }
    }


    public AuthPage(MessageSystem msys) {
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String tml = this.loadPage("AuthPage.html");
        response.getWriter().println(tml);
        response.setContentType("text/html;charset=utf-8");

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

//
//      Validation of Password and login
//
        if (true) {
            HttpSession session = request.getSession();
            session.setAttribute("user", login);
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
