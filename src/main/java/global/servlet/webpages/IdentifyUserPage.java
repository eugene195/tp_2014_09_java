package global.servlet.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.io.PrintWriter;
import org.json.JSONObject;

/**
 * Created by Moiseev Maxim on 30.09.2014.
 * The page for a define user.
 */
public class IdentifyUserPage extends WebPage {
    public static final String URL = "/identifyuser";

    private long getUserId(HttpSession session) {
        Object userObjId = session.getAttribute("userId");
        if (userObjId != null) {
            return (long) userObjId;
        }
        return -1;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession(false);
        if (session == null) {
            json.put("status", FAILED);
        } else {
            long userId = getUserId(session);
            if (userId == -1) {
                json.put("status", FAILED);
            } else {
                json.put("status", OK);
                json.put("login", session.getAttribute("login").toString());
                System.out.append("login:").append(session.getAttribute("login").toString());
            }
        }

        printout.print(json);
        printout.flush();

    }


}
