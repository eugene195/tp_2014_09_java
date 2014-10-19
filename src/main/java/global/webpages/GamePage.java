package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 28.08.2014.
 */
public class GamePage extends WebPage {
    public static final String URL = "/game";
    public static final String TML_PATH = "GamePage.html";

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        Map<String, Object> context = new LinkedHashMap<>();

        HttpSession session = request.getSession(false);
        if (session != null) {
            context.put("myName", session.getAttribute("login").toString());
        }
        else {
            //TODO: Send error to AuthPage
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect(AuthPage.URL);
        }
        String page = this.generateHTML(TML_PATH, context);

        response.getWriter().print(page);
        response.setContentType(WebPage.CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
