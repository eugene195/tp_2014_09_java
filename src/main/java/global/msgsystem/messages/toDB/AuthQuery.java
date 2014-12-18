package global.msgsystem.messages.toDB;

import global.DataBaseManager;
import global.models.UserSession;

/**
 * Created by Евгений on 28.08.2014.
 */

public class AuthQuery extends AbstractToDB {
    private final UserSession userSession;
    private final String password;

    public AuthQuery(UserSession userSession, String password){
        this.userSession = userSession;
        this.password = password;
    }

    @Override
    public void exec(DataBaseManager dbman){
        dbman.checkAuth(userSession, password);
    }
}
