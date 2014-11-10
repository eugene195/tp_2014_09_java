package global.database.dataSets;

public class UsersDataSet {
    private long id;
    private String login;
    private String passw;
    private String score;

    public UsersDataSet(long id, String login, String passw, String score){
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
    public String getScore() {
        return score;
    }
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "score: "+ this.score;
    }
}
