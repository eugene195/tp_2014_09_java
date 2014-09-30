package global.models;

/**
 * Created by max on 26.09.14.
 */
public class UserSession {
    private final String login;

    private boolean successAuth;
    private Long userId;

    public UserSession(String login) {
        this.successAuth = false;
        this.login = login;
        this.userId = -1L;
    }

    public boolean isAuthSuccess(){
        return this.successAuth;
    }

    public void setSuccessAuth(boolean successAuth) {
        this.successAuth = successAuth;
    }

    public String getLogin(){
        return this.login;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
