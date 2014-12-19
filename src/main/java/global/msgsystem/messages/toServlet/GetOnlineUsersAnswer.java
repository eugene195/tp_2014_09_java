package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.servlet.webpages.AdminPage;

import java.util.Set;

/**
 * Created by eugene on 9/27/14.
 */
public class GetOnlineUsersAnswer extends AbstractToServlet {
    private Set<String> keyset;

    public GetOnlineUsersAnswer(String addressFrom, Set<String> set){
        super(addressFrom);
        this.keyset = set;
    }

    public Set<String> getSet(){
        return keyset;
    }

    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(AdminPage.URL, this);
    }
}
