package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.servlet.webpages.AuthPage;

/**
 * Created by eugene on 9/27/14.
 */
public class GetOnlineUsersQuery extends AbstractToServlet {
    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(AuthPage.URL, this);
    }
}
