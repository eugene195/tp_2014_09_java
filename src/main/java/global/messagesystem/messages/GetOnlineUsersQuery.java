package global.messagesystem.messages;

import global.Servlet;
import global.webpages.AuthPage;

/**
 * Created by eugene on 9/27/14.
 */
public class GetOnlineUsersQuery extends AbstractMsg {
    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof Servlet){
            Servlet servlet = (Servlet)abonent;
            servlet.transmitToPage(AuthPage.URL, this);
        }
        else{
            System.out.println("GetUsersQuery exception during execution");
        }
    }
}