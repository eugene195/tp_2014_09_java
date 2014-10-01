package global.messages;
import global.Servlet;
import global.webpages.AuthPage;
/**
 * Created by max on 26.09.14.
 */
public class LogoutMsg extends AbstractMsg {
    private final String login;
    public LogoutMsg(String login) {
        this.login = login;
    }
    public String getLogin() {
        return this.login;
    }
    @Override
    public void exec(Runnable abonent) {
        if(abonent instanceof Servlet) {
            Servlet srv = (Servlet) abonent;
            srv.transmitToPage(AuthPage.URL, this);
        }
        else{
            System.out.println("LogoutMsg exception during execution");
        }
    }
}