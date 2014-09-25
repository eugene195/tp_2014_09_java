package global.webpages;

import global.MessageSystem;
import global.messages.AbstractMsg;
import global.messages.ProfileInfoAnswer;
import global.messages.ProfileInfoQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 28.08.2014.
 * The Profile page with settings and battle statistics
 */
public class ProfilePage extends WebPage {

    public static final String URL = "/profile";
    public static final String TML_PATH = "ProfilePage.html";

    protected HttpSession session;
    private final MessageSystem msys;

    public ProfilePage(MessageSystem msys) {
        this.msys = msys;
    }

    private long getUserId(HttpServletRequest request) {
        Object userObjId = this.session.getAttribute("userId");
        if (userObjId != null) {
            return (long) userObjId;
        }

        return -1;
    }
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        this.session = request.getSession(false);
        if (this.session == null) {
            //TODO: Send error to AuthPage
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect(AuthPage.URL);
            return;
        }

        long userId = this.getUserId(request);
        if (userId == -1) {
            //TODO: Send error to AuthPage
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect(AuthPage.URL);
            return;
        }

        this.msys.sendMessage(new ProfileInfoQuery(userId), "dbman");
        this.setZombie();


        Map<String, Object> context = new LinkedHashMap<>();
        context.put("login", session.getAttribute("login").toString());
        String page = this.generateHTML(TML_PATH, context);
        response.getWriter().print(page);

        response.setContentType(WebPage.CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void finalize(AbstractMsg abs_msg) {
        if (abs_msg instanceof ProfileInfoAnswer) {
            ProfileInfoAnswer msg = (ProfileInfoAnswer) abs_msg;
            this.resume();
        }
    }

}
