package global.msgsystem.messages.toDB;

import global.DataBaseManager;

/**
 * Created by Moiseev Maxim on 02.10.14.
 */

public class ChangePasswordQuery extends AbstractToDB {
    private final String login;
    private final String curPassw;
    private final String newPassw;

    public ChangePasswordQuery(String addressFrom, String login, String curPassw, String newPassw) {
        super(addressFrom);
        this.login = login;
        this.curPassw = curPassw;
        this.newPassw = newPassw;
    }

    @Override
    public void exec(DataBaseManager dbman) {
        dbman.changePassword(getAddressFrom(), login, curPassw, newPassw);
    }
}
