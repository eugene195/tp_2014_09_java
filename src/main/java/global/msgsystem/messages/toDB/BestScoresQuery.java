package global.msgsystem.messages.toDB;

import global.DataBaseManager;

/**
 * Created by max on 02.10.14.
 */
public class BestScoresQuery extends AbstractToDB {
    @Override
    public void exec(DataBaseManager dbman) {
         dbman.bestScores();
    }
}
