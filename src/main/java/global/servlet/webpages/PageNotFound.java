package global.servlet.webpages;

import global.MessageSystem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by max on 28.08.14.
 */
public class PageNotFound extends WebPage {

    public PageNotFound(String address, MessageSystem msys) {
        super(address, msys);
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType(WebPage.CONTENT_TYPE);
        response.getWriter().println("<h1>Page not Found</h1>");
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType(WebPage.CONTENT_TYPE);
        response.getWriter().println("<h1>Page not Found</h1>");
    }
}
