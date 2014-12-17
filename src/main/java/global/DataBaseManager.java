package global;

import global.models.UserSession;

/**
 * Created by eugene on 10/11/14.
 */
public interface DataBaseManager extends Runnable, Abonent {

    void getUsers();

    void checkAuth(UserSession userSession, String passw);

    void registerUser(String login, String passw);

    void bestScores();

    void changePassword(String login, String curPassw, String newPassw);

}
