package global.msgsystem.messages;

import global.GameMechanics;

/**
 * Created by max on 13.11.14.
 */
public class GameSessionsQuery extends AbstractMsg {
    @Override
    public void exec(Runnable abonent) {
        if (abonent instanceof GameMechanics) {
            GameMechanics mechanics = (GameMechanics) abonent;
            mechanics.getGameSessions();
        } else {
            System.out.println("Error during GameSessionsQuery");
        }
    }
}
