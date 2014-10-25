package global.messagesystem.messages;

import global.database.DataBaseManagerImpl;
import global.database.DataBaseManager;

/**
 * Created by Евгений on 01.09.2014.
 */
public class RegistrationQuery extends AbstractMsg {
    private final String login;
    private final String password;

    public RegistrationQuery(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(Runnable abonent) {
        if(abonent instanceof DataBaseManagerImpl) {
            DataBaseManager dbman = (DataBaseManagerImpl)abonent;
            dbman.registerUser(this.login, this.password);
        }
        else {
            System.out.println("AuthQuery exception during execution");
        }
    }
}
