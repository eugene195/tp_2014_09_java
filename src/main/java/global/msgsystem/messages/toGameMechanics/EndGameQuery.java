package global.msgsystem.messages.toGameMechanics;

import global.GameMechanics;
import global.engine.Engine;
/**
 * Created by max on 22.11.14.
 */
public class EndGameQuery extends AbstractToMechanics {
    private Engine engine;
    private Long winnerSnakeId;

    public EndGameQuery(Engine engine, Long winnerSnakeId) {
        this.engine = engine;
        this.winnerSnakeId = winnerSnakeId;
    }

    @Override
    public void exec(GameMechanics mechanics) {
        mechanics.endGame(engine, winnerSnakeId);
    }
}
