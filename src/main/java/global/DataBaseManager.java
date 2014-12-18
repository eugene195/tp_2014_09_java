package global;

import global.models.UserSession;

import java.util.Map;

/**
 * Created by eugene on 10/11/14.
 */
public interface DataBaseManager extends Runnable {

    void getUsers();

    void checkAuth(UserSession userSession, String passw);

    void registerUser(String login, String passw);

    void bestScores();

    void changeScores(Map<String, Integer> extraScoresUsers);

    void changePassword(String login, String curPassw, String newPassw);

}
