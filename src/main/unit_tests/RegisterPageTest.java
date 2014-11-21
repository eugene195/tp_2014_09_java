import global.MessageSystem;
import global.msgsystem.messages.toServlet.RegistrationAnswer;
import global.servlet.webpages.RegisterPage;
import org.junit.*;
import utils.MinMessageHelper;
import utils.PrintHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterPageTest {
    private static RegisterPage testPage;
    private static MessageSystem msys;

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;

    static class TestTask implements Runnable {
        private final RegisterPage testPage;
        private final boolean isSuccess;

        public TestTask(RegisterPage testPage, boolean isSuccess) {
            this.testPage = testPage;
            this.isSuccess = isSuccess;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(10);

                    if (this.testPage.isZombie()) {
                        String login = "max";
                        String error = "";
                        boolean success = this.isSuccess;

                        this.testPage.finalizeAsync(new RegistrationAnswer(success, login, error));
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

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        testPage = new RegisterPage(msys);
    }

    @After
    public void tearDown() throws Exception {

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

        testJson = "{\"message\":\"Username already exists\",\"status\":\"-1\"}";
        Assert.assertEquals("JSON is invalid (1) " + json, testJson, json);

        helper.fflush();

        (new Thread(new TestTask(testPage, true))).start();
        testPage.handlePost(request, response);
        json = helper.getPrintOut();

        testJson = "{\"status\":\"1\"}";
        Assert.assertEquals("JSON is invalid (2) " + json, testJson, json);
    }
}