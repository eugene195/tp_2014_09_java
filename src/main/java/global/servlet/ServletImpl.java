package global.servlet;

import global.AddressService;
import global.MessageSystem;
import global.Servlet;
import global.models.UserSession;
import global.msgsystem.messages.toServlet.AbstractToServlet;
import global.servlet.webpages.*;

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

public class ServletImpl extends HttpServlet implements Servlet {
    private static Pattern patternUrl =  Pattern.compile("^/\\w+");
    private final Map<String, WebPage> pageMap = new HashMap<>();
    private final Map<String, UserSession> userSessions = new HashMap<>();

    private final MessageSystem msys;
    private final String address;

    public ServletImpl(MessageSystem msys) {
        this.address = AddressService.registerServlet();
        this.msys = msys;
        msys.register(this);

        this.pageMap.put(AuthPage.URL, new AuthPage(address, this.msys, this.userSessions));
        this.pageMap.put(GamePage.URL, new GamePage(address, this.msys));
        this.pageMap.put(RegisterPage.URL, new RegisterPage(address, this.msys));
        this.pageMap.put(ProfilePage.URL, new ProfilePage(address, this.msys));
        this.pageMap.put(LogoutPage.URL, new LogoutPage(address, this.msys, this.userSessions));
        this.pageMap.put(IdentifyUserPage.URL, new IdentifyUserPage(address, this.msys));
        this.pageMap.put(AdminPage.URL, new AdminPage(address, this.msys));
        this.pageMap.put(ScorePage.URL, new ScorePage(address, this.msys));
        this.pageMap.put(GameListPage.URL, new GameListPage(address, this.msys));
    }

    @Override
    public String getAddress() {
        return this.address;
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
        WebPage currentPage = getPageByURL(request.getRequestURI());
        currentPage.handleGet(request, response);
    }

    private WebPage getPageByURL(String requestURI) {
        Matcher matcher = patternUrl.matcher(requestURI);

        System.out.println(requestURI);
        if (matcher.find()) {
            String requestURL = matcher.group();
            WebPage currentPage = this.pageMap.get(requestURL);

            if (currentPage != null) {
                return currentPage;
            }
        }

        return new PageNotFound(address, msys);
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        WebPage currentPage = getPageByURL(request.getRequestURI());
        currentPage.handlePost(request, response);
    }

    @Override
    public void transmitToPage(String URL, AbstractToServlet msg) {
        WebPage page = this.pageMap.get(URL);
        if (page != null) {
            page.finalizeAsync(msg);
        }
    }

}
