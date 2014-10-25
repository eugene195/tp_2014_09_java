package global.messagesystem.messages;

import global.Servlet;
import global.models.UserSession;
import global.webpages.AuthPage;

/**
 * Created by Евгений on 28.08.2014.
 */
public class AuthAnswer extends AbstractMsg {
    private final UserSession userSession;

    public AuthAnswer(UserSession userSession) {
        this.userSession = userSession;
    }

    public UserSession getUserSession(){
        return this.userSession;
    }

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof Servlet){
            Servlet srv = (Servlet) abonent;
            srv.transmitToPage(AuthPage.URL, this);
        }
        else{
            System.out.println("AuthAnswer exception during execution");
        }
    }
}
