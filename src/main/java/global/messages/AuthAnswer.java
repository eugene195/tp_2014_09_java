package global.messages;

import global.Servlet;
import global.webpages.AuthPage;

/**
 * Created by Евгений on 28.08.2014.
 */
public class AuthAnswer extends AbstractMsg {
    private final boolean success;
    private final String login;
    private final long userId;

    public AuthAnswer(boolean success, String login, long userId) {
        super();
        this.success = success;
        this.login = login;
        this.userId = userId;
    }

    public boolean isAuthSuccess(){
        return this.success;
    }

    public String getLogin(){
        return this.login;
    }

    public Long getUserId() {
        return this.userId;
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
