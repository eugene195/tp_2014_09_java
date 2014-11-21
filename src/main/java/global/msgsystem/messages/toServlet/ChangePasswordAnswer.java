package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.servlet.webpages.ProfilePage;

/**
 * Created by Moiseev Maxim on 02.10.14.
 */
public class ChangePasswordAnswer  extends AbstractToServlet {
    private final boolean success;
    private final String errorMessage;

    public ChangePasswordAnswer(boolean success, String errMsg) {
        this.success = success;
        this.errorMessage = errMsg;
    }

    public boolean isChangePasswordSuccess() {
        return this.success;
    }

    public String getErrMsg() { return this.errorMessage; }

    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(ProfilePage.URL, this);
    }
}
