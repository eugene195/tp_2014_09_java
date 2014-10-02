package global.models;

import java.io.Serializable;

/**
 * Created by max on 02.10.14.
 */
public class Score implements Serializable {
    public String login;
    public int score;

    public Score(String login, int score) {
        this.login = login;
        this.score = score;
    }
}
