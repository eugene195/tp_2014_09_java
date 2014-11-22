package global.msgsystem.messages.toDB;

import global.DataBaseManager;

/**
 * Created by eugene on 9/27/14.
 */
public class GetUsersQuery extends AbstractToDB {

    @Override
    public void exec(DataBaseManager dbman) {
        dbman.getUsers();
    }
}
