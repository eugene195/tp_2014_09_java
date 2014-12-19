package global;

import global.models.UserSession;

import java.util.Map;

/**
 * Created by eugene on 10/11/14.
 */
public interface DataBaseManager extends Runnable, Abonent {

    void getUsers(String addressTo);

    void checkAuth(String addressTo, UserSession userSession, String passw);

    void registerUser(String addressTo, String login, String passw);

    void bestScores(String addressTo);

    void changeScores(String addressTo, Map<String, Integer> extraScoresUsers);

    void changePassword(String addressTo, String login, String curPassw, String newPassw);

}
