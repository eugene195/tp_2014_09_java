package global.models;

import java.util.ArrayList;

/**
 * Created by eugene on 10/19/14.
 */
public class Player {
    private static final int DEFAULT_SCORE = 0;

    private final String name;
    private long score;
    private int snakeId;

    public Player(String name, int snakeId) {
        this.snakeId = snakeId;
        this.score = DEFAULT_SCORE;
        this.name = name;
    }

    public void setScore(long newScore) {
        if (score >= DEFAULT_SCORE) {
            this.score = newScore;
        }
    }

    public long getScore() {
        return this.score;
    }

    public int getSnake() {
        return this.snakeId;
    }

    public String getName() { return this.name; }
}
