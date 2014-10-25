package global.messagesystem.messages;

import global.database.DataBaseManagerImpl;
import global.database.DataBaseManager;
import global.models.UserSession;

/**
 * Created by Евгений on 28.08.2014.
 */
public class AuthQuery extends AbstractMsg {
    private final UserSession userSession;
    private final String password;

    public AuthQuery(UserSession userSession, String password){
        this.userSession = userSession;
        this.password = password;
    }

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof DataBaseManagerImpl){
            DataBaseManager dbman = (DataBaseManagerImpl)abonent;
            dbman.checkAuth(this.userSession, this.password);
        }
        else{
            System.out.println("AuthQuery exception during execution");
        }
    }
}
