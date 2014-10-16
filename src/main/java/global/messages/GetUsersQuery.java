package global.messages;

import global.DataBaseManagerImpl;
import global.base.DataBaseManager;

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
