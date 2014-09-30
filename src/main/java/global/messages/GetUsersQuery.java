package global.messages;

import global.DataBaseManager;

/**
 * Created by eugene on 9/27/14.
 */
public class GetUsersQuery extends AbstractMsg {

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof DataBaseManager){
            DataBaseManager dbman = (DataBaseManager)abonent;
            dbman.getUsers();
        }
        else{
            System.out.println("GetUsersQuery exception during execution");
        }
    }
}
