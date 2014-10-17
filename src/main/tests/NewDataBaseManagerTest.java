import global.implementations.DataBaseManagerImpl;
import global.MessageSystem;
import global.models.UserSession;
import org.junit.*;

import static org.junit.Assert.*;

public class NewDataBaseManagerTest {

    private static DataBaseManagerImpl dbMan;
    private final String username = "firstUser";
    private final String userpass = "Userpass";
    private final String newuserpass = "NewUserpass";

//    Already registered
    private final String regUsername = "max";
    private final String regUserpass = "11";

    private final UserSession session = new UserSession(username);
    private final UserSession regUserSession = new UserSession(regUsername);

    @BeforeClass
    public static void setUp() throws Exception {
        MessageSystem msys = new MessageSystem();
        try {
            dbMan = new DataBaseManagerImpl(msys);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        if (dbMan.userExists(username)) {
            dbMan.deleteUser(username);
        }
        dbMan.changePassword(regUsername,  newuserpass, regUserpass);
    }

    @Test
    public void testChangePasswordFalse() throws Exception {
        Assert.assertFalse(dbMan.changePassword(username, userpass, newuserpass));
    }

    @Test
    public void testChangePasswordTrue() throws Exception {
        Assert.assertTrue(dbMan.changePassword(regUsername, regUserpass, newuserpass));
    }

    @Test
    public void testGetUsers() throws Exception {
        Assert.assertTrue(dbMan.getUsers());
    }

    @Test
    public void testCheckAuthFalse() throws Exception {
        Assert.assertFalse(dbMan.checkAuth(session, userpass));
    }

    @Test
    public void testCheckAuthTrue() throws Exception {
        Assert.assertTrue(dbMan.checkAuth(regUserSession, regUserpass));
    }

    @Test
    public void testRegisterUser() throws Exception {
        Assert.assertTrue(dbMan.registerUser(username, userpass));
        Assert.assertFalse(dbMan.registerUser(username, userpass));
    }

    @Test
    public void testBestScores() throws Exception {
        Assert.assertTrue(dbMan.bestScores());
    }

}