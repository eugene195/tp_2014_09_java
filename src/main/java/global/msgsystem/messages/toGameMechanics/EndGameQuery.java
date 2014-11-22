package global.msgsystem.messages.toGameMechanics;

import global.GameMechanics;
import global.engine.Engine;
/**
 * Created by max on 22.11.14.
 */
public class EndGameQuery extends AbstractToMechanics {
    private Engine engine;

    public EndGameQuery(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec(GameMechanics mechanics) {
        mechanics.endGame(this.engine);
    }
}
