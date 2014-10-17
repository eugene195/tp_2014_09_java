package global;

import global.models.UserSession;

/**
 * Created by eugene on 10/11/14.
 */
public interface DataBaseManager extends Runnable {

    public boolean getUsers();

    public boolean checkAuth(UserSession userSession, String passw);

    public boolean registerUser(String login, String passw);

    public boolean bestScores();

    public boolean changePassword(String login, String curPassw, String newPassw);

    public void getProfileInfo(long userId);

}
