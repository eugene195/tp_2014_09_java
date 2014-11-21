package global.servlet.webpages;
import global.AddressService;
import global.MessageSystem;
import global.database.dataSets.UserDataSet;
import global.msgsystem.messages.*;
import global.msgsystem.messages.toServlet.AbstractToServlet;
import global.msgsystem.messages.toServlet.GetOnlineUsersAnswer;
import global.msgsystem.messages.toServlet.GetOnlineUsersQuery;
import global.msgsystem.messages.toServlet.GetUsersAnswer;
import global.msgsystem.messages.toDB.GetUsersQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Created by eugene on 9/20/14.
 */
public class AdminPage extends WebPage {

    public static final String URL = "/admin";
    public static final String TML_PATH = "AdminPage.html";

    private ArrayList<UserDataSet> registered;
    private Set<String> loggedIn;

    private int fullInfo;
    private final MessageSystem msys;

    public AdminPage(MessageSystem msys) {
        this.msys = msys;
        this.registered = new ArrayList<UserDataSet>();
        this.loggedIn = new HashSet();
    }
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        fullInfo = 0;

        this.msys.sendMessage(new GetUsersQuery(), "dbman");
        this.msys.sendMessage(new GetOnlineUsersQuery(), "servlet");
        setZombie();

        Map<String, Object> context = new LinkedHashMap<>();
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String timeString = request.getParameter("shutdown");
        String localParam = request.getParameter("locally");

        PrintWriter printout = response.getWriter();
        if (timeString != null) {

            System.out.print("Server will be down after: "+ timeString + " ms");

            int timeMS = Integer.valueOf(timeString);
            try {
                Thread.sleep(timeMS);
            }
            catch (Exception e) {
                System.out.println("Zombie was interrupted");
            }

            System.out.print("\nShutdown");
            printout.print("Shutdown");

            if (localParam == null)
                System.exit(0);
        }
        else {
            printout.print("No time parameter");
        }

        context.put("status", "run");
        context.put("registered", this.registered);
        context.put("loggedIn", this.loggedIn);
        context.put("regCount", this.registered.size());
        context.put("logCount", this.loggedIn.size());

        String page = generateHTML(TML_PATH, context);
        printout.print(page);
    }

    @Override
    public void finalizeAsync(AbstractToServlet absMsg) {

        if (absMsg instanceof GetUsersAnswer) {
            GetUsersAnswer msg = (GetUsersAnswer) absMsg;
            this.registered = msg.getUsers();
            fullInfo++;
        }
        else if (absMsg instanceof GetOnlineUsersAnswer) {
            GetOnlineUsersAnswer msg = (GetOnlineUsersAnswer) absMsg;
            this.loggedIn = msg.getSet();
            fullInfo++;
        }
        if (fullInfo == 2)
            resume();
    }
}