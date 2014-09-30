package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.PrintWriter;
import org.json.JSONObject;

/**
 * Created by Moiseev Maxim on 30.09.2014.
 * The page for a define user.
 */
public class IdentifyUserPage extends WebPage {
    public static final String URL = "/identifyuser";


    public IdentifyUserPage() {
        super();

    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {

    }

    private long getUserId(HttpSession session) {
        Object userObjId = session.getAttribute("userId");
        if (userObjId != null) {
            return (long) userObjId;
        }
        return -1;
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = response.getWriter();
        JSONObject JObject = new JSONObject();
        HttpSession session = request.getSession(false);
        if (session == null) {
            JObject.put("status", "-1");
        } else {
            long userId = this.getUserId(session);
            if (userId == -1) {
                JObject.put("status", "-1");
            } else {
                JObject.put("status", "1");
                JObject.put("login", session.getAttribute("login").toString());
                System.out.append("login:").append(session.getAttribute("login").toString());
            }
        }

        printout.print(JObject);
        printout.flush();

    }


}
