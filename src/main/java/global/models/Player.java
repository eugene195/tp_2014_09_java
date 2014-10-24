package global.models;

import java.util.ArrayList;

/**
 * Created by eugene on 10/19/14.
 */
public class Player {

    private final int DEFAULT_SCORE = 0;
    private final String myName;
    private String enemyName;

    private Score myScore;

    private int enemyScore = 0;

    private ArrayList<String> enemies;

    public Player(String myName) {
        this.myName = myName;
        this.myScore = new Score(myName, DEFAULT_SCORE);
    }

    public String getMyName() {
        return myName;
    }

    public ArrayList<String> getEnemyNames() {
        return this.enemies;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getMyScore() {
        return myScore.getScore();
    }

//    TODO
    public int getEnemyScore() {
        return enemyScore;
    }

    public void incrementMyScore() {

        myScore.increment(1);
    }

//    TODO
    public void incrementEnemyScore() {
        enemyScore++;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }
}
