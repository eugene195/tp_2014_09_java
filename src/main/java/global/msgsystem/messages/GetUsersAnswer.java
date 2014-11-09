package global.msgsystem.messages;

import global.Servlet;
import global.database.dataSets.UsersDataSet;
import global.servlet.webpages.AdminPage;

import java.util.ArrayList;

/**
 * Created by eugene on 9/27/14.
 */
public class GetUsersAnswer extends AbstractMsg {

    private ArrayList<UsersDataSet> users;
    public GetUsersAnswer(ArrayList<UsersDataSet> users){
        this.users = users;
    }

    public ArrayList<UsersDataSet> getUsers() {
        return this.users;
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
