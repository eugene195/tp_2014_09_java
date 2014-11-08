<<<<<<< HEAD

import global.msgsystem.MessageSystem;
import global.msgsystem.MessageSystemImpl;
=======
import global.MessageSystem;
>>>>>>> master
import global.msgsystem.messages.AuthAnswer;

import global.models.UserSession;
import global.servlet.webpages.AuthPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.MinMessageHelper;
import utils.PrintHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.*;

public class AuthPageTest {

    private static AuthPage testPage;
    private static MessageSystemImpl msys;

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;


    static class TestTask implements Runnable {
        private final AuthPage testPage;
        private final boolean isSuccess;

        public TestTask(AuthPage testPage, boolean isSuccess) {
            this.testPage = testPage;
            this.isSuccess = isSuccess;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(10);

                    if (this.testPage.isZombie()) {
                        UserSession us = new UserSession("max");
                        us.setSuccessAuth(this.isSuccess);

                        this.testPage.finalizeAsync(new AuthAnswer(us));
                        break;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @BeforeClass
    public static void setUp() throws Exception {
        msys = new MinMessageHelper();
        Map<String, UserSession> userSessions = new HashMap<>();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        testPage = new AuthPage(msys, userSessions);
    }

    @Test
    public void testHandlePost() throws Exception {
        PrintHelper helper = new PrintHelper();
        when(response.getWriter()).thenReturn(helper);
        String json, testJson;

        when(request.getParameter("login")).thenReturn("max");
        when(request.getParameter("passw")).thenReturn("11");

        (new Thread(new TestTask(testPage, false))).start();
        testPage.handlePost(request, response);
        json = helper.getPrintOut();

        testJson = "{\"message\":\"Incorrect login or password\",\"status\":\"-1\"}";
        Assert.assertEquals("JSON is invalid (1) " + json, testJson, json);

        helper.fflush();

        (new Thread(new TestTask(testPage, true))).start();
        testPage.handlePost(request, response);
        json = helper.getPrintOut();

        testJson = "{\"status\":\"1\"}";
        Assert.assertEquals("JSON is invalid (2) " + json, testJson, json);
    }
}