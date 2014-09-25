package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by eugene on 9/20/14.
 */
public class AdminPage extends WebPage {
    public static final String URL = "/admin";
    public static final String TML_PATH = "AdminPage.html";
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String timeString = request.getParameter("shutdown");
        Map<String, Object> context = new LinkedHashMap<>();

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if(timeString != null){
            int timeMS = Integer.valueOf(timeString);
            System.out.print("Server will be down after: "+ timeMS + " ms");
//            SLEEP
            System.out.print("\nShutdown");
            System.exit(0);
        }
        context.put("status", "run");
        String page = this.generateHTML(TML_PATH, context);
        response.getWriter().print(page);
    }
}
