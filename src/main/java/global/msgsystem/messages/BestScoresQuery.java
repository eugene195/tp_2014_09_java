package global.msgsystem.messages;

import global.database.DataBaseManagerImpl;
import global.DataBaseManager;

/**
 * Created by max on 02.10.14.
 */
public class BestScoresQuery extends AbstractMsg {

    @Override
    public void exec(Runnable runnable) {
         if (runnable instanceof DataBaseManagerImpl) {
             DataBaseManager dbman = (DataBaseManagerImpl) runnable;
             dbman.bestScores();
         }
    }
}
