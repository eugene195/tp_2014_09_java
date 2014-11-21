package global.msgsystem.messages.toDB;

import global.DataBaseManager;

/**
 * Created by Moiseev Maxim on 02.10.14.
 */

public class ChangePasswordQuery extends AbstractToDB {
    private final String login;
    private final String curPassw;
    private final String newPassw;

    public ChangePasswordQuery(String login, String curPassw, String newPassw) {
        this.login = login;
        this.curPassw = curPassw;
        this.newPassw = newPassw;
    }

    @Override
    public void exec(DataBaseManager dbman) {
        dbman.changePassword(this.login, this.curPassw, this.newPassw);
    }
}
