package global.webpages;

import global.MessageSystem;
import global.messages.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Евгений on 28.08.2014.
 */
public class RegisterPage extends WebPage{
    public static final String URL = "/register";
    public static final String TML_PATH = "RegisterPage.html";
    private final MessageSystem msys;

    private boolean successReg;
    private String loginReg;
    private boolean notValid;

    public RegisterPage(MessageSystem msys) {
        super();
        this.msys = msys;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        Map<String, Object> context = new LinkedHashMap<>();

        String page = this.generateHTML(TML_PATH, context);
        response.getWriter().print(page);
        response.setContentType(WebPage.CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String login = request.getParameter("login");
        String passw = request.getParameter("passw");
        String repeatPassw = request.getParameter("repeatPassw");

        if(!passw.equals(repeatPassw)){
            response.getWriter().println("Passwords do not match");
            response.setContentType(WebPage.CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        this.msys.sendMessage(new RegistrationQuery(login, passw), "dbman");
        this.setZombie();

        if (this.successReg) {
//            Стоит ли создавать сессию? Или отправлять на AuthPage?
//            HttpSession session = request.getSession();
//            session.setAttribute("login", this.loginAuth);
//            session.setAttribute("userId", this.userId);
//            response.sendRedirect(ProfilePage.URL);
            response.sendRedirect(AuthPage.URL);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            this.notValid = true;
            response.sendRedirect(RegisterPage.URL);
        }
    }


    @Override
    public void finalize(AbstractMsg abs_msg) {
        if (abs_msg instanceof RegistrationAnswer) {
            RegistrationAnswer msg = (RegistrationAnswer) abs_msg;

            this.successReg = msg.isRegistrationSuccess();
            this.loginReg = msg.getLogin();
            this.resume();
        }
    }
}
