package global.msgsystem.messages;

/**
 * Created by Евгений on 06.09.2014.
 */

import global.Servlet;
import global.servlet.webpages.RegisterPage;

/**
 * Created by Евгений on 28.08.2014.
 */

public class RegistrationAnswer extends AbstractMsg {
    private final boolean success;
    private final String login;
    private final String errorMessage;

    public RegistrationAnswer(boolean success, String login, String errMsg) {
        this.success = success;
        this.login = login;
        this.errorMessage = errMsg;
    }


    public boolean isRegistrationSuccess() {
        return this.success;
    }

    public String getLogin() {
        return this.login;
    }

    public String getErrMsg() { return this.errorMessage; }

    @Override
    public void exec(Runnable abonent) {
        if(abonent instanceof Servlet) {
            Servlet srv = (Servlet) abonent;
            srv.transmitToPage(RegisterPage.URL, this);
        }
        else{
            System.out.println("RegistrationAnswer exception during execution");
        }
    }
}


