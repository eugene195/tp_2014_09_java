package global;

import global.messages.AbstractMsg;
import global.models.UserSession;
import global.webpages.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * Created by Евгений on 26.08.2014.
 * Extended jetty servlet.
 */

public class Servlet extends HttpServlet implements Runnable {

    private static final String SERVLET_ADDRESS = "servlet";
    private final MessageSystem msys;
    private final Map<String, WebPage> pageMap = new HashMap<>();
    private final Map<String, UserSession> userSessions = new HashMap<>();

    public Servlet(MessageSystem msys) {
        this.msys = msys;
        msys.register(this, SERVLET_ADDRESS);

        this.pageMap.put(AuthPage.URL, new AuthPage(this.msys, this.userSessions));
        this.pageMap.put(GamePage.URL, new GamePage());
        this.pageMap.put(RegisterPage.URL, new RegisterPage(this.msys));
        this.pageMap.put(ProfilePage.URL, new ProfilePage(this.msys));
        this.pageMap.put(LogoutPage.URL, new LogoutPage(this.msys, this.userSessions));
        this.pageMap.put(IdentifyUserPage.URL, new IdentifyUserPage());
        this.pageMap.put(AdminPage.URL, new AdminPage(this.msys));
        this.pageMap.put(ScorePage.URL, new ScorePage(this.msys));
    }

    @Override
    public void run() {
        while (true) {
            this.msys.executeFor(this);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        WebPage currentPage = this.getPageByURL(request.getRequestURI());
        currentPage.handleGet(request, response);
    }

    private WebPage getPageByURL(String requestURI) {
        Matcher matcher = Pattern.compile("^/\\w+").matcher(requestURI);

        System.out.println(requestURI);
        if (matcher.find()) {
            String requestURL = matcher.group();
            // System.out.println(requestURL);
            WebPage currentPage = this.pageMap.get(requestURL);

            if (currentPage != null) {
                return currentPage;
            }
        }

        return new PageNotFound();
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        WebPage currentPage = this.getPageByURL(request.getRequestURI());
        currentPage.handlePost(request, response);
    }

    public void transmitToPage(String URL, AbstractMsg msg) {
        WebPage page = this.pageMap.get(URL);
        if (page != null) {
            page.finalize(msg);
        }
    }
}
