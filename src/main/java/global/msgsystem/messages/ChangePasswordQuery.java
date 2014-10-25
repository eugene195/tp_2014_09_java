package global.msgsystem.messages;

import global.database.DataBaseManagerImpl;
import global.DataBaseManager;

/**
 * Created by Moiseev Maxim on 02.10.14.
 */

public class ChangePasswordQuery extends AbstractMsg {
    private final String login;
    private final String curPassw;
    private final String newPassw;

    public ChangePasswordQuery(String login, String curPassw, String newPassw) {
        this.login = login;
        this.curPassw = curPassw;
        this.newPassw = newPassw;
    }

    @Override
    public void exec(Runnable abonent) {
        if(abonent instanceof DataBaseManagerImpl) {
            DataBaseManager dbman = (DataBaseManagerImpl)abonent;
            dbman.changePassword(this.login, this.curPassw, this.newPassw);
        }
        else {
            System.out.println("ChangePasswordQuery exception during execution");
        }
    }
}
