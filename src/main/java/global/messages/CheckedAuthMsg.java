package global.messages;

import global.Servlet;
import global.webpages.AuthPage;

/**
 * Created by Евгений on 28.08.2014.
 */
public class CheckedAuthMsg extends AbstractMsg {
    private boolean success;
    private String login;

    public CheckedAuthMsg(boolean success, String login) {
        this.success = success;
        this.login = login;
    }

    public boolean isAuthSuccess(){
        return this.success;
    }

    public String getLogin(){
        return this.login;
    }

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof Servlet){
            Servlet srv = (Servlet) abonent;
            srv.transmitToPage(AuthPage.URL, this);
        }
    }
}
