package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.database.dataSets.UserDataSet;
import global.msgsystem.messages.AbstractMsg;
import global.servlet.webpages.AdminPage;

import java.util.ArrayList;

/**
 * Created by eugene on 9/27/14.
 */
public class GetUsersAnswer extends AbstractToServlet {

    private ArrayList<UserDataSet> users;
    public GetUsersAnswer(ArrayList<UserDataSet> users){
        this.users = users;
    }

    public ArrayList<UserDataSet> getUsers() {
        return users;
    }

    @Override
    public void exec(Servlet servlet) {
        servlet.transmitToPage(AdminPage.URL, this);
    }
}
