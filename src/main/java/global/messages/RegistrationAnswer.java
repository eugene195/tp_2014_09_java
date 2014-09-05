package global.messages;

/**
 * Created by Евгений on 06.09.2014.
 */

import global.Servlet;
import global.webpages.RegisterPage;

/**
 * Created by Евгений on 28.08.2014.
 */

public class RegistrationAnswer extends AbstractMsg {
    private final boolean success;
    private final String login;

    public RegistrationAnswer(boolean success, String login) {
        super();
        this.success = success;
        this.login = login;
    }

    public boolean isRegistrationSuccess(){
        return this.success;
    }

    public String getLogin(){
        return this.login;
    }

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof Servlet){
            Servlet srv = (Servlet) abonent;
            srv.transmitToPage(RegisterPage.URL, this);
        }
        else{
            System.out.println("RegistrationAnswer exception during execution");
        }
    }
}


