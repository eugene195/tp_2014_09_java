package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by max on 28.08.14.
 */
public class PageNotFound extends WebPage {
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("<h1>Page not Found</h1>");
    }
}
