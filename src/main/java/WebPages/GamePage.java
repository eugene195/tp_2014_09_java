package WebPages;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 28.08.2014.
 */
public class GamePage implements WebPage {
    @Override
    public void loadPage(HttpServletResponse response)
            throws IOException
    {
        response.getWriter().println("<h1>Hello GAME PAGE</h1>");
    }
}
