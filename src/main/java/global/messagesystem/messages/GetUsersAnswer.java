package global.messagesystem.messages;

import global.Servlet;
import global.webpages.AdminPage;

import java.util.HashMap;

/**
 * Created by eugene on 9/27/14.
 */
public class GetUsersAnswer extends AbstractMsg {

    private HashMap<String, Long> map;
    public GetUsersAnswer(HashMap<String, Long> newMap){
        this.map = newMap;
    }

    public HashMap<String, Long> getMap(){
        return this.map;
    }

    @Override
    public void exec(Runnable abonent) {
        if (abonent instanceof Servlet) {
            Servlet servlet = (Servlet) abonent;
            servlet.transmitToPage(AdminPage.URL, this);
        }
        else{
            System.out.println("ProfileInfoAnswer exception during execution");
        }
    }
}
