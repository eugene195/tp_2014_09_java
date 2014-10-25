package global.msgsystem.messages;

import global.servlet.ServletImpl;
import global.Servlet;
import global.servlet.webpages.AdminPage;

import java.util.Set;

/**
 * Created by eugene on 9/27/14.
 */
public class GetOnlineUsersAnswer extends AbstractMsg {

    private Set<String> keyset;
    public GetOnlineUsersAnswer(Set<String> set){
        this.keyset = set;
    }

    public Set<String> getSet(){
        return this.keyset;
    }

    @Override
    public void exec(Runnable abonent) {
        if (abonent instanceof Servlet) {
            Servlet servlet = (ServletImpl) abonent;
            servlet.transmitToPage(AdminPage.URL, this);
        }
        else{
            System.out.println("ProfileInfoAnswer exception during execution");
        }
    }
}
