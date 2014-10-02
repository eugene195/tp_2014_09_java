package global.messages;

import global.DataBaseManager;

/**
 * Created by max on 02.10.14.
 */
public class BestScoresQuery extends AbstractMsg {

    @Override
    public void exec(Runnable runnable) {
         if (runnable instanceof DataBaseManager) {
             DataBaseManager dbman = (DataBaseManager) runnable;
             dbman.bestScores();
         }
    }
}
