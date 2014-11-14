import global.database.dataSets.UserDataSet;
import global.msgsystem.messages.GetOnlineUsersAnswer;
import global.msgsystem.messages.GetUsersAnswer;
import global.servlet.webpages.AdminPage;

import org.junit.*;
import utils.MinMessageHelper;
import utils.PrintHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminPageTest {

    static class TestTask implements Runnable {
        private final AdminPage testPage;

        public TestTask(AdminPage testPage) {
            this.testPage = testPage;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(10);

                    if (this.testPage.isZombie()) {
                        this.testPage.finalizeAsync(new GetUsersAnswer(new ArrayList<UserDataSet>()));
                        this.testPage.finalizeAsync(new GetOnlineUsersAnswer(new HashSet<>()));
                        break;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static MinMessageHelper msys;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static AdminPage testPage;

    @BeforeClass
    public static void setUp() throws Exception {
        msys = new MinMessageHelper();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getParameter("locally")).thenReturn("true");

        testPage = new AdminPage(msys);
    }

    @Test
    public void testHandleGet() throws Exception {
        PrintHelper helper = new PrintHelper();
        when(response.getWriter()).thenReturn(helper);
        String answer, testAnswer;


        (new Thread(new TestTask(testPage))).start();

        testPage.handleGet(request, response);
        answer = helper.getPrintOut();

        testAnswer = "No time parameter";
        assertEquals("Incorrect answer (1)" + answer, testAnswer, answer);

        when(request.getParameter("shutdown")).thenReturn("100");
        helper.fflush();

        (new Thread(new TestTask(testPage))).start();
        testPage.handleGet(request, response);
        answer = helper.getPrintOut();

        testAnswer = "Shutdown";
        assertEquals("Incorrect answer (2)" + answer, testAnswer, answer);
    }
}