package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.servlet.webpages.AuthPage;

/**
 * Created by eugene on 9/27/14.
 */
public class GetOnlineUsersQuery extends AbstractToServlet {
    public GetOnlineUsersQuery(String addressFrom, String addressTo) {
        super(addressFrom, addressTo);
    }

    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(AuthPage.URL, this);
    }
}
