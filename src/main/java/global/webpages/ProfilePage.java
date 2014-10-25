package global.webpages;

import global.messagesystem.MessageSystem;
import global.messagesystem.messages.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.io.PrintWriter;

/**
 * Created by Евгений on 28.08.2014.
 * The Profile page with settings and battle statistics
 */
public class ProfilePage extends WebPage {

    public static final String URL = "/profile";

    private final MessageSystem msys;

    private boolean successChangeProfile;
    private String messageError;

    public ProfilePage(MessageSystem msys) {
        this.msys = msys;
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String passw = request.getParameter("newPassw");
        String repeatPassw = request.getParameter("confirmPassw");
        String curPassw = request.getParameter("curPassw");

        HttpSession session = request.getSession(false);

        if (session == null || passw == null || repeatPassw == null || curPassw == null
            || curPassw == "" || passw == "" || !passw.equals(repeatPassw)) {
            response.setContentType(WebPage.CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String login = session.getAttribute("login").toString();

        this.msys.sendMessage(new ChangePasswordQuery(login, curPassw, passw), "dbman");
        this.setZombie();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject JObject = new JSONObject();

        if (this.successChangeProfile) {
            JObject.put("status", "1");
        }
        else {
            JObject.put("status", "-1");
            JObject.put("message", messageError);
        }
        printout.print(JObject);
        printout.flush();
    }


    @Override
    public void finalizeAsync(AbstractMsg abs_msg) {
        if (abs_msg instanceof ChangePasswordAnswer) {
            ChangePasswordAnswer msg = (ChangePasswordAnswer) abs_msg;
            this.successChangeProfile = msg.isChangePasswordSuccess();
            this.messageError = msg.getErrMsg();
            this.resume();
        }
    }

}
