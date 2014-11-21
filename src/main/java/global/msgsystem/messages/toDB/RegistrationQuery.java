package global.msgsystem.messages.toDB;

import global.DataBaseManager;

/**
 * Created by Евгений on 01.09.2014.
 */
public class RegistrationQuery extends AbstractToDB {
    private final String login;
    private final String password;

    public RegistrationQuery(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(DataBaseManager dbman) {
        dbman.registerUser(this.login, this.password);
    }
}
