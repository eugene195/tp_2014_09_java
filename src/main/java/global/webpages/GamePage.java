package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 28.08.2014.
 */
public class GamePage extends WebPage {
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        loadPage(request.getPathInfo());
        response.getWriter().println("<h1>Hello GAME PAGE</h1>");




        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
