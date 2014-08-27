import WebPages.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Евгений on 26.08.2014.
 */

public class Servlet extends HttpServlet {
    public Map<String, WebPage> PageMap;
    public Servlet(){
        super();
        PageMap = new HashMap<String, WebPage>();
        PageMap.put( "/auth", new AuthPage());
        PageMap.put( "/game", new GamePage());
        PageMap.put( "/register", new RegisterPage());
        PageMap.put( "/stat", new StatPage());
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        WebPage currentPage = parseRequest(request);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        if(currentPage == null)
            response.getWriter().println("<h1>Page not Found</h1>");
        else
            currentPage.loadPage(response);
    }

    public WebPage parseRequest(HttpServletRequest request){
        String requestURI = request.getRequestURI();

        /* DEBUG */
        System.out.println(requestURI);

        return PageMap.get(requestURI);
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
    }
}
