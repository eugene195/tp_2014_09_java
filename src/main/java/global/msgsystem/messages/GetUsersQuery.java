package global.msgsystem.messages;

import global.database.DataBaseManagerImpl;
import global.DataBaseManager;

/**
 * Created by eugene on 9/27/14.
 */
public class GetUsersQuery extends AbstractMsg {

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof DataBaseManagerImpl){
            DataBaseManager dbman = (DataBaseManagerImpl)abonent;
            dbman.getUsers();
        }
        else{
            System.out.println("GetUsersQuery exception during execution");
        }
    }
}
