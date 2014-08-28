package global.messages;

import global.Servlet;

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

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof Servlet){
            Servlet srv = (Servlet) abonent;
            srv.transmit(this);
        }
    }
}
