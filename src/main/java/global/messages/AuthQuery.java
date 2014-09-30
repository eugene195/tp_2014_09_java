package global.messages;

import global.DataBaseManager;
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
        if(abonent instanceof DataBaseManager){
            DataBaseManager dbman = (DataBaseManager)abonent;
            dbman.checkAuth(this.userSession, this.password);
        }
        else{
            System.out.println("AuthQuery exception during execution");
        }
    }
}
