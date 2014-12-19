package global.msgsystem.messages.toDB;

import global.DataBaseManager;

/**
 * Created by Евгений on 01.09.2014.
 */
public class RegistrationQuery extends AbstractToDB {
    private final String login;
    private final String password;

    public RegistrationQuery(String addressFrom, String login, String password) {
        super(addressFrom);
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(DataBaseManager dbman) {
        dbman.registerUser(getAddressFrom(), login, password);
    }
}
