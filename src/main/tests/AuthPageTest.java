import global.MessageSystem;
import global.models.UserSession;
import global.webpages.AuthPage;
import junit.framework.TestCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class AuthPageTest extends TestCase {

    private Map<String, UserSession> userSessions;
    private MessageSystem msys;
    private HttpServletRequest request;
    private HttpSession session;
    private AuthPage testPage;
    private UserSession userSession;


    public void setUp() throws Exception {
        super.setUp();
        this.userSessions = new HashMap<>();

        this.msys = new MessageSystem();
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);

        this.userSession = mock(UserSession.class);
        when(this.request.getSession()).thenReturn(this.session);

        try {
            this.testPage = new AuthPage(this.msys, this.userSessions);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void tearDown() throws Exception {

    }

    public void testHandlePost() throws Exception {
        final String login = "max";
        final String passw = "11";

        when(this.request.getParameter("login")).thenReturn(login);
        when(this.request.getParameter("passw")).thenReturn(passw);
    }

    public void testFinalize() throws Exception {

    }
}