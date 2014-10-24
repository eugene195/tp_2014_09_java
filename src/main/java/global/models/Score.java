package global.models;

import java.io.Serializable;

/**
 * Created by max on 02.10.14.
 */
public class Score implements Serializable {
    private String login;
    private int score;

    public Score(String login, int score) {
        this.login = login;
        this.score = score;
    }

    public void increment(int value) {
        this.score += value;
    }

    public String getLogin() {
        return this.login;
    }

    public int getScore() {
        return this.score;
    }
}
