package global.tests;

import global.DataBaseManagerImpl;
import global.MessageSystem;
import global.models.UserSession;
import junit.framework.TestCase;
import org.junit.Assert;

public class DataBaseManagerImplTest extends TestCase {

    private DataBaseManagerImpl dbMan;
    private final String username = "firstUser";
    private final String userpass = "Userpass";
    private final String newuserpass = "NewUserpass";

    public void setUp() throws Exception {
        super.setUp();
        MessageSystem msys = new MessageSystem();
        try {
            dbMan = new DataBaseManagerImpl(msys);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void tearDown() throws Exception {
        if (dbMan.userExists(username)) {
            dbMan.deleteUser(username);
        }
    }

    public void testDB() {

        UserSession session = new UserSession(username);


        // Check non-existent pass
        Assert.assertFalse("Sql exception during changePassword()", dbMan.changePassword(username, userpass, newuserpass));

        // authorize non-existent user
        Assert.assertFalse(dbMan.checkAuth(session, userpass));

        // Register new user
        Assert.assertTrue(dbMan.registerUser(username, userpass));

        // Try to register user that already exists
        Assert.assertFalse("User with this login already Exists", dbMan.registerUser(username, userpass));

        // authorize new user
        Assert.assertTrue(dbMan.checkAuth(session, userpass));

        // Change new user pass
        Assert.assertTrue(dbMan.changePassword(username, userpass, newuserpass));

        // Should almost always be true
        Assert.assertTrue(dbMan.getUsers());
        Assert.assertTrue(dbMan.bestScores());
    }
}