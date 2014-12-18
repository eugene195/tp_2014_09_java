package global.msgsystem.messages.toServlet;

/**
 * Created by Евгений on 06.09.2014.
 */

import global.Servlet;
import global.msgsystem.messages.AbstractMsg;
import global.servlet.webpages.RegisterPage;

/**
 * Created by Евгений on 28.08.2014.
 */

public class RegistrationAnswer extends AbstractToServlet {
    private final boolean success;
    private final String login;
    private final String errorMessage;

    public RegistrationAnswer(boolean success, String login, String errMsg) {
        this.success = success;
        this.login = login;
        this.errorMessage = errMsg;
    }

    public boolean isRegistrationSuccess() {
        return success;
    }

    public String getLogin() {
        return login;
    }

    public String getErrMsg() { return errorMessage; }

    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(RegisterPage.URL, this);
    }
}


