package global.msgsystem.messages.toGameMechanics;

import global.GameMechanics;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 13.11.14.
 */
public class GameSessionsQuery extends AbstractToMechanics {
    public GameSessionsQuery(String addressFrom) {
        super(addressFrom);
    }

    @Override
    public void exec(GameMechanics mechanics) {
        mechanics.getGameSessions(getAddressFrom());
    }
}
