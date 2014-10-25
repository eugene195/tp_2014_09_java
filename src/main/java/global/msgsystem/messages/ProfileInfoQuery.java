package global.msgsystem.messages;

import global.database.DataBaseManagerImpl;
import global.DataBaseManager;

/**
 * Created by max on 31.08.14.
 */
public class ProfileInfoQuery extends AbstractMsg {
    private final long userId;

    public ProfileInfoQuery(long userId) {
        this.userId = userId;
    }

    @Override
    public void exec(Runnable abonent) {
        if (abonent instanceof DataBaseManagerImpl) {
            DataBaseManager dbman = (DataBaseManagerImpl) abonent;
            dbman.getProfileInfo(this.userId);
        }
        else {
            System.out.println("ProfileInfoQuery exception during execution");
        }
    }
}
