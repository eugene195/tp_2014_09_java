import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Created by Евгений on 26.08.2014.
 * Extended jetty servlet.
 */

public class Servlet extends HttpServlet implements Runnable {
    private static final String SERVLET_ADDRESS = "servlet";

    private final MessageSystem msys;

    public Servlet(MessageSystem msys){
        super();
        this.msys = msys;
        msys.register(this, SERVLET_ADDRESS);
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
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello World</h1>");
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
    }
}
