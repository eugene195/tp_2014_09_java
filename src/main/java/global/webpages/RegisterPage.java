package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 28.08.2014.
 */
public class RegisterPage extends WebPage{
    public static final String URL = "/register";
    public static final String TML_PATH = "";

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.getWriter().println("<h1>Hello REGISTER PAGE</h1>");

        response.setContentType(WebPage.CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
