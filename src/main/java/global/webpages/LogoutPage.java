package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 31.08.2014.
 */
public class LogoutPage extends WebPage {
    private static final String TML_PATH = "LogoutPage.html";

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        Map<String, Object> context = new LinkedHashMap<>();
        ArrayList<String> errorList = new ArrayList<>();

        HttpSession session = request.getSession(false);
        if(session != null) {
            context.put("login", session.getAttribute("login").toString());
            session.invalidate();
        }
        else {
            errorList.add("Sorry. You haven't been authorized yet");
        }
        context.put("errorList", errorList);
        String page = this.generateHTML(TML_PATH, context);
        response.getWriter().print(page);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
