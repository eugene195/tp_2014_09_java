import global.database.DataBaseManagerImpl;
import global.database.dataSets.UserDataSet;

import global.models.Score;
import global.models.User;
import global.models.UserSession;
import global.msgsystem.messages.toServlet.*;
import org.junit.*;
import utils.MessageHelper;

import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseManagerTest {
    private static final String address = "";
    private static MessageHelper msys = new MessageHelper();
    private static ArrayList<User> registeredUsersList = new ArrayList<>();
    private static ArrayList<User> unRegisteredUsersList = new ArrayList<>();
    private static DataBaseManagerImpl dbMan;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            dbMan = new DataBaseManagerImpl(msys, "test_java_db", "test_user", "drovosek");
            registeredUsersList.add(new User("firstPass", "firstUser"));
            registeredUsersList.add(new User("secondPass", "secondUser"));
            registeredUsersList.add(new User("11", "max"));

            unRegisteredUsersList.add(new User("fUnregPass", "fUnregLogin"));
            unRegisteredUsersList.add(new User("sUnregPass", "sUnregLogin"));
            unRegisteredUsersList.add(new User("tUnregPass", "tUnregLogin"));

            for (User user : registeredUsersList)
                dbMan.registerUser(address, user.getLogin(), user.getPass());
        }
        catch (SQLException xc) {
            System.out.println("Cannot connect to DataBase");
            throw xc;
        }
    }

    @AfterClass
    public static void tearDown() {
        //for (User user : registeredUsersList)
            //dbMan.deleteUser(user.getLogin());
    }

    @Test
    public void testRegisterUser() throws Exception {
        String login = unRegisteredUsersList.get(0).getLogin(),
                pass = unRegisteredUsersList.get(0).getPass();
        dbMan.registerUser(address, login, pass);
        RegistrationAnswer msg = msys.getMsg();

        Assert.assertEquals("Passed registration test", "", msg.getErrMsg());
        Assert.assertEquals("Passed registration test", true, msg.isRegistrationSuccess());

        dbMan.registerUser(address, login, pass);
        msg = msys.getMsg();

        Assert.assertEquals("Duplicate registration test", "User with this login already Exists", msg.getErrMsg());
        Assert.assertEquals("Duplicate registration test", false, msg.isRegistrationSuccess());
        dbMan.deleteUser(login);
    }


    @Test
    public void testCheckAuth() throws Exception {
        String login = unRegisteredUsersList.get(1).getLogin(),
                pass = unRegisteredUsersList.get(1).getPass();
        UserSession failSession = new UserSession(login);

        dbMan.checkAuth(address, failSession, pass);
        AuthAnswer msg = msys.getMsg();
        Assert.assertEquals("Wrong user authorisation test", false, msg.getUserSession().isAuthSuccess());

        login = registeredUsersList.get(1).getLogin();
        pass = registeredUsersList.get(1).getPass();
        UserSession passSession = new UserSession(login);
        dbMan.checkAuth(address, passSession, pass);
        msg = msys.getMsg();
        Assert.assertEquals("Right user authorisation test", true, msg.getUserSession().isAuthSuccess());

    }

    @Test
    public void testChangePasswordFalse() throws Exception {
        String login = unRegisteredUsersList.get(0).getLogin(),
               pass = unRegisteredUsersList.get(0).getPass();
        dbMan.changePassword(address, login, pass, "newPass");

        ChangePasswordAnswer msg = msys.getMsg();

        Assert.assertEquals("Failed change pass", "Wrong current password", msg.getErrMsg());
        Assert.assertEquals("Failed change pass", false, msg.isChangePasswordSuccess());

        login = registeredUsersList.get(0).getLogin();
        pass = registeredUsersList.get(0).getPass();

        dbMan.changePassword(address, login, pass, "newPass");

        msg = msys.getMsg();

        Assert.assertEquals("Passed change pass", "", msg.getErrMsg());
        Assert.assertEquals("Passed change pass", true, msg.isChangePasswordSuccess());

        dbMan.changePassword(address, login, "newPass", pass);
    }

    @Test
    public void testGetUsers() throws Exception {
        dbMan.getUsers(address);
        GetUsersAnswer msg = msys.getMsg();

        ArrayList<String> userLogins = new ArrayList();
        userLogins.add("firstUser");
        userLogins.add("secondUser");
        userLogins.add("max");

        ArrayList<UserDataSet> users =  msg.getUsers();

        for (int i = 0; i < users.size(); ++i) {
            Assert.assertEquals("Get user testing", userLogins.get(i), users.get(i).getLogin());
        }
    }

    @Test
    public void testBestScores() throws Exception {
        final int ZERO = 0;
        dbMan.bestScores(address);
        BestScoresAnswer msg = msys.getMsg();

        ArrayList<Score> scores = new ArrayList<>();
        ArrayList<Score> answerScores = msg.getScores();

        scores.add(new Score("firstUser", ZERO));
        scores.add(new Score("secondUser", ZERO));
        scores.add(new Score("max", ZERO));

        Assert.assertEquals("Best scores compare size", scores.size(), answerScores.size());

        for (int i = 0; i < scores.size(); ++i) {
            Assert.assertEquals("Best scores compare login", scores.get(i).getLogin(), answerScores.get(i).getLogin());
            Assert.assertEquals("Best scores compare login", scores.get(i).getScore(), answerScores.get(i).getScore());
        }
    }

}