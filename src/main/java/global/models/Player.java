package global.models;

import java.util.ArrayList;

/**
 * Created by eugene on 10/19/14.
 */
public class Player {

    private final int DEFAULT_SCORE = 0;
    private final String myName;
    private String enemyName;

    //private ArrayList<String> enemies;

    public Player(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

//    public ArrayList<String> getEnemyNames() {
//        return this.enemies;
//    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }
}
