package WebPages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 28.08.2014.
 * The page for a user authentication.
 */
public class AuthPage extends WebPage {
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String tml = this.loadPage("AuthPage.html");


        response.getWriter().println(tml);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");
        System.out.println(login + "  " + passw);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
