package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.servlet.webpages.ProfilePage;

/**
 * Created by Moiseev Maxim on 02.10.14.
 */
public class ChangePasswordAnswer  extends AbstractToServlet {
    private final boolean success;
    private final String errorMessage;

    public ChangePasswordAnswer(String addressFrom, String addressTo, boolean success, String errMsg) {
        super(addressFrom, addressTo);
        this.success = success;
        this.errorMessage = errMsg;
    }

    public boolean isChangePasswordSuccess() {
        return success;
    }

    public String getErrMsg() { return errorMessage; }

    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(ProfilePage.URL, this);
    }
}
