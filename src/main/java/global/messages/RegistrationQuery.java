package global.messages;

import global.DataBaseManager;

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
        if(abonent instanceof DataBaseManager) {
            DataBaseManager dbman = (DataBaseManager)abonent;
            dbman.registerUser(this.login, this.password);
        }
        else {
            System.out.println("AuthQuery exception during execution");
        }
    }
}
