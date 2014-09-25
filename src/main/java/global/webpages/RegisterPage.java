package global.webpages;

import global.MessageSystem;
import global.messages.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 28.08.2014.
 */
public class RegisterPage extends WebPage {
    public static final String URL = "/register";
    public static final String TML_PATH = "RegisterPage.html";
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
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("errorList", this.msgList);
        String page = this.generateHTML(TML_PATH, context);

        response.getWriter().print(page);
        response.setContentType(WebPage.CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);

        this.msgList.clear();
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");
        String repeatPassw = request.getParameter("repeatPassw");

        if (!passw.equals(repeatPassw)) {
            response.getWriter().println("Passwords do not match");
            response.setContentType(WebPage.CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        this.msys.sendMessage(new RegistrationQuery(login, passw), "dbman");
        this.setZombie();

        if (this.successReg) {
            response.sendRedirect(AuthPage.URL);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.sendRedirect(RegisterPage.URL);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
