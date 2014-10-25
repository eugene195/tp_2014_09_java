package global.msgsystem.messages;

import global.Servlet;
import global.servlet.webpages.ProfilePage;

/**
 * Created by Moiseev Maxim on 02.10.14.
 */
public class ChangePasswordAnswer  extends AbstractMsg {
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
    public void exec(Runnable abonent) {
        if(abonent instanceof Servlet) {
            Servlet srv = (Servlet) abonent;
            srv.transmitToPage(ProfilePage.URL, this);
        }
        else{
            System.out.println("RegistrationAnswer exception during execution");
        }
    }
}