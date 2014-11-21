package global.servlet.webpages;

import global.AddressService;
import global.MessageSystem;
import global.msgsystem.messages.toServlet.AbstractToServlet;
import global.msgsystem.messages.toServlet.ChangePasswordAnswer;
import global.msgsystem.messages.toDB.ChangePasswordQuery;
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

        String login = session.getAttribute("login").toString();

        this.msys.sendMessage(new ChangePasswordQuery(login, curPassw, passw), AddressService.getDBManAddr());
        this.setZombie();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject json = new JSONObject();

        if (this.successChangeProfile) {
            json.put("status", "1");
        }
        else {
            json.put("status", "-1");
            json.put("message", messageError);
        }
        printout.print(json);
        printout.flush();
    }


    @Override
    public void finalizeAsync(AbstractToServlet absMsg) {
        if (absMsg instanceof ChangePasswordAnswer) {
            ChangePasswordAnswer msg = (ChangePasswordAnswer) absMsg;
            this.successChangeProfile = msg.isChangePasswordSuccess();
            this.messageError = msg.getErrMsg();
            this.resume();
        }
    }

}
