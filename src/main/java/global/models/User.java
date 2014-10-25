package global.models;

/**
 * Created by eugene on 10/18/14.
 */
public class User {
    private String password;
    private String login;

    public User (String pass, String login) {
        this.password = pass;
        this.login = login;
    }

    public String getPass() {
        return this.password;
    }

    public String getLogin() {
        return this.login;
    }
}
