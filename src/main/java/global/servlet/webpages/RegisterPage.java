package global.servlet.webpages;

import global.MessageSystem;
import global.msgsystem.messages.*;

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
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        PrintWriter printout = response.getWriter();
        msgList.forEach(printout::print);
        this.msgList.clear();
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");

        this.msys.sendMessage(new RegistrationQuery(login, passw), "dbman");
        this.setZombie();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject jObject = new JSONObject();

        if (this.successReg)
            jObject.put("status", OK);
        else {
            jObject.put("status", FAILED);
            jObject.put("message", "Username already exists");
        }
        printout.print(jObject);
    }


    @Override
    public void finalizeAsync(AbstractMsg abs_msg) {
        if (abs_msg instanceof RegistrationAnswer) {
            RegistrationAnswer msg = (RegistrationAnswer) abs_msg;

            this.successReg = msg.isRegistrationSuccess();
            this.msgList.add(msg.getErrMsg());
            this.resume();
        }
    }
}
