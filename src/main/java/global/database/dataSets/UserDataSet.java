package global.database.dataSets;

public class UserDataSet {
    private long id;
    private String login;
    private int score;

    public UserDataSet(long id, String login, int score){
        this.id = id;
        this.login = login;
        this.score = score;
    }

    public String getLogin() {
        return login;
    }
    public int getScore() {
        return score;
    }
    public long getId() {
        return id;
    }

}