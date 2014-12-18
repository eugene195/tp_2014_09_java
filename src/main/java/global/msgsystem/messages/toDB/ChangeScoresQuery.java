package global.msgsystem.messages.toDB;


import global.DataBaseManager;

import java.util.HashMap;
import java.util.Map;

public class ChangeScoresQuery extends AbstractToDB{
    private final Map<String, Integer> extraScoresUsers = new HashMap<>();

    public ChangeScoresQuery(Map<String, Integer> extraScoresUsers) {
        this.extraScoresUsers.putAll(extraScoresUsers);
    }

    @Override
    public void exec(DataBaseManager dbman) {
        dbman.changeScores(extraScoresUsers);
    }
}
