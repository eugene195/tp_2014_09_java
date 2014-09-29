package global.webpages;

import global.MessageSystem;
import global.messages.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import java.io.PrintWriter;
import org.json.JSONObject;


/**
 * Created by Евгений on 28.08.2014.
 */
public class RegisterPage extends WebPage {
    public static final String URL = "/register";

    private final MessageSystem msys;

    private boolean successReg;
    private final ArrayList<String> msgList = new ArrayList<>();

    public RegisterPage(MessageSystem msys) {
        super();
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {


        this.msgList.clear();
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");
        String repeatPassw = request.getParameter("passw2");

        if (!passw.equals(repeatPassw)) {
            response.setContentType(WebPage.CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        this.msys.sendMessage(new RegistrationQuery(login, passw), "dbman");
        this.setZombie();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject JObject = new JSONObject();

        if (this.successReg) {
            JObject.put("status", "1");

        }
        else {
            JObject.put("status", "-1");
            JObject.put("message", "Username already exists");
        }
        printout.print(JObject);
        printout.flush();
    }


    @Override
    public void finalize(AbstractMsg abs_msg) {
        if (abs_msg instanceof RegistrationAnswer) {
            RegistrationAnswer msg = (RegistrationAnswer) abs_msg;

            this.successReg = msg.isRegistrationSuccess();
            this.msgList.add(msg.getErrMsg());
            this.resume();
        }
    }
}
