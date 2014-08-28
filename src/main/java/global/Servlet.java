package global;

import global.messages.AbstractMsg;
import global.messages.CheckedAuthMsg;
import global.webpages.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created by Евгений on 26.08.2014.
 * Extended jetty servlet.
 */

public class Servlet extends HttpServlet implements Runnable {

    private static final String SERVLET_ADDRESS = "servlet";
    private final MessageSystem msys;
    private final Map<String, WebPage> pageMap = new HashMap<>();

    public Servlet(MessageSystem msys){
        super();
        this.msys = msys;
        msys.register(this, SERVLET_ADDRESS);

        this.pageMap.put("/auth", new AuthPage(this.msys));
        this.pageMap.put("/game", new GamePage());
        this.pageMap.put("/register", new RegisterPage());
        this.pageMap.put("/stat", new StatPage());
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
        WebPage currentPage = this.getPageByURL(request);
        currentPage.handleGet(request, response);
    }

    private WebPage getPageByURL(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        WebPage currentPage = this.pageMap.get(requestURI);

        System.out.println(requestURI);

        if (currentPage == null) {
            currentPage = new PageNotFound();
        }
        return currentPage;
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        WebPage currentPage = this.getPageByURL(request);
        currentPage.handlePost(request, response);
    }

    public void transmit(CheckedAuthMsg msg){
        WebPage page = this.pageMap.get("/auth");
        if ( (page != null) && (page instanceof AuthPage) ) {
            AuthPage aPage = (AuthPage) page;
            aPage.finalizeAuth(msg);
        }
    }
}
