import global.implementations.DataBaseManagerImpl;
import global.MessageSystem;
import global.messages.AbstractMsg;
import global.messages.BestScoresAnswer;
import global.messages.ChangePasswordAnswer;
import global.models.Score;
import global.models.User;
import global.models.UserSession;
import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.mock;





public class DataBaseManagerTest {


    static class MessageHelper extends MessageSystem {
        private AbstractMsg msg;

        @Override
        public boolean sendMessage(AbstractMsg msg, String unused) {
            this.msg = msg;
            return true;
        }

        public <MsgType extends AbstractMsg> MsgType getMsg () {
            return (MsgType) this.msg;
        }
    }

    static private MessageHelper msys = new MessageHelper();
    static private ArrayList<User> userList = new ArrayList<>();
    static private DataBaseManagerImpl dbMan;

    @BeforeClass
    static public void setUp() throws Exception {
        try {
            dbMan = new DataBaseManagerImpl(msys, "test_java_db", "test_user", "drovosek");
            userList.add(new User("firstPass", "firstUser"));
            userList.add(new User("secondPass", "secondUser"));
            userList.add(new User("11", "max"));
        }
        catch (SQLException xc) {
            System.out.println("Cannot connect to DataBase");
            throw xc;
        }
    }

    @Test
    public void testChangePasswordFalse() throws Exception {
        String login = userList.get(0).getLogin(),
               pass = userList.get(0).getPass();
        dbMan.changePassword(login, pass, "newPass");

        ChangePasswordAnswer msg = msys.getMsg();
        ChangePasswordAnswer rightTest = new ChangePasswordAnswer(false, "Failed to change password");
        Assert.assertNotEquals("Change password comparison failed", msg, rightTest);
    }

    @Test
    public void testChangePasswordTrue() throws Exception {
//        Assert.assertTrue(dbMan.changePassword(regUsername, regUserpass, newuserpass));
    }

    @Test
    public void testGetUsers() throws Exception {
//        Assert.assertTrue(dbMan.getUsers());
    }

    @Test
    public void testCheckAuthFalse() throws Exception {
//        UserSession userSession = new UserSession(login);
//        Assert.assertFalse(dbMan.checkAuth(session, userpass));
    }

    @Test
    public void testCheckAuthTrue() throws Exception {
//        Assert.assertTrue(dbMan.checkAuth(regUserSession, regUserpass));
    }

    @Test
    public void testRegisterUser() throws Exception {
//        Assert.assertTrue(dbMan.registerUser(username, userpass));
//        Assert.assertFalse(dbMan.registerUser(username, userpass));
    }

    //@Test
    public void testBestScores() throws Exception {
        dbMan.bestScores();
        BestScoresAnswer msg = msys.getMsg();
        ArrayList<Score> scores = new ArrayList<Score>();
        scores.add(new Score("firstUser", 190));
        scores.add(new Score("max", 75));
        scores.add(new Score("secondUser", 70));
        BestScoresAnswer rightTest = new BestScoresAnswer(scores);
        Assert.assertEquals("Best scores failed", rightTest, msg);
    }

}