package global.webpages;

import global.MessageSystem;
import global.messages.LogoutMsg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 31.08.2014.
 */
public class LogoutPage extends WebPage {
    public static final String URL = "/logout";
    private static final String TML_PATH = "LogoutPage.html";
    private final MessageSystem msys;

    public LogoutPage(MessageSystem msys) {
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        Map<String, Object> context = new LinkedHashMap<>();

        HttpSession session = request.getSession(false);
        if(session != null) {
            String login = session.getAttribute("login").toString();
            context.put("login", login);
            this.msys.sendMessage(new LogoutMsg(login), "servlet");
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
