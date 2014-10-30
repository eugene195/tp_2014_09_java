package global.msgsystem.messages;

import global.Servlet;
import global.servlet.webpages.ProfilePage;

/**
 * Created by max on 31.08.14.
 */
public class ProfileInfoAnswer extends AbstractMsg {

    @Override
    public void exec(Runnable abonent) {
        if (abonent instanceof Servlet) {
            Servlet servlet = (Servlet) abonent;
            servlet.transmitToPage(ProfilePage.URL, this);
        }
        else{
            System.out.println("ProfileInfoAnswer exception during execution");
        }
    }
}
