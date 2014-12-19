package global.servlet.webpages;

import global.AddressService;
import global.MessageSystem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import java.io.PrintWriter;

import global.msgsystem.messages.toServlet.AbstractToServlet;
import global.msgsystem.messages.toServlet.RegistrationAnswer;
import global.msgsystem.messages.toDB.RegistrationQuery;
import org.json.JSONObject;


/**
 * Created by Евгений on 28.08.2014.
 */
public class RegisterPage extends WebPage {

    public static final String URL = "/register";
    private boolean successReg;
    private final ArrayList<String> msgList = new ArrayList<>();

    public RegisterPage(String address, MessageSystem msys) {
        super(address, msys);
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

        this.msys.sendMessage(new RegistrationQuery(address, login, passw), AddressService.getDBManAddr());
        setZombie();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject json = new JSONObject();

        if (this.successReg)
            json.put("status", OK);
        else {
            json.put("status", FAILED);
            json.put("message", "Username already exists");
        }
        printout.print(json);
    }


    @Override
    public void finalizeAsync(AbstractToServlet absMsg) {
        if (absMsg instanceof RegistrationAnswer) {
            RegistrationAnswer msg = (RegistrationAnswer) absMsg;

            this.successReg = msg.isRegistrationSuccess();
            this.msgList.add(msg.getErrMsg());
            resume();
        }
    }
}
