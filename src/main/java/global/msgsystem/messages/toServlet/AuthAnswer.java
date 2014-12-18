package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.models.UserSession;
import global.servlet.webpages.AuthPage;

/**
 * Created by Евгений on 28.08.2014.
 */
public class AuthAnswer extends AbstractToServlet {
    private final UserSession userSession;

    public AuthAnswer(UserSession userSession) {
        this.userSession = userSession;
    }

    public UserSession getUserSession(){
        return userSession;
    }

    @Override
    public void exec(Servlet servlet){
        servlet.transmitToPage(AuthPage.URL, this);
    }
}
