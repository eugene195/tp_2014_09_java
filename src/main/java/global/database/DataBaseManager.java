package global.database;

import global.models.UserSession;

/**
 * Created by eugene on 10/11/14.
 */
public interface DataBaseManager extends Runnable {

    public void getUsers();

    public void checkAuth(UserSession userSession, String passw);

    public void registerUser(String login, String passw);

    public void bestScores();

    public void changePassword(String login, String curPassw, String newPassw);

    public void getProfileInfo(long userId);

}
