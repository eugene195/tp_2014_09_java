import WebPages.*;

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

        pageMap.put("/auth", new AuthPage());
        pageMap.put("/game", new GamePage());
        pageMap.put("/register", new RegisterPage());
        pageMap.put("/stat", new StatPage());
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
        WebPage currentPage = getPageByURL(request);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (currentPage == null)
            response.getWriter().println("<h1>Page not Found</h1>");
        else
            currentPage.loadPage(response);
    }

    private WebPage getPageByURL(HttpServletRequest request){
        String requestURI = request.getRequestURI();

        /* DEBUG */
        // System.out.print(requestURI + ":::" + request.getPathInfo() + "\n");

        return pageMap.get(requestURI);
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
    }
}
