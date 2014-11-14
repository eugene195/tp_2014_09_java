package global.database.dataSets;

public class UserDataSet {
    private long id;
    private String login;
    private String passw;
    private int score;

    public UserDataSet(long id, String login, String passw, int score){
        this.id = id;
        this.login = login;
        this.passw = passw;
        this.score = score;
    }

    public String getLogin() {
        return login;
    }
    public String getPassw() {
        return passw;
    }
    public int getScore() {
        return score;
    }
    public long getId() {
        return id;
    }

}