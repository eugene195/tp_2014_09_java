package global.servlet.webpages;
import global.MessageSystem;
import global.database.dataSets.UsersDataSet;
import global.msgsystem.messages.*;
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

    private ArrayList<UsersDataSet> registered;
    private Set<String> loggedIn;

    private int fullInfo;
    private final MessageSystem msys;

    public AdminPage(MessageSystem msys) {
        this.msys = msys;
        this.registered = new ArrayList<UsersDataSet>();
        this.loggedIn = new HashSet();
    }
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        fullInfo = 0;
        this.msys.sendMessage(new GetUsersQuery(), "dbman");
        this.msys.sendMessage(new GetOnlineUsersQuery(), "servlet");
        this.setZombie();

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

        String page = this.generateHTML(TML_PATH, context);
        printout.print(page);
    }

    public void finalizeAsync(AbstractMsg abs_msg) {

        if (abs_msg instanceof GetUsersAnswer) {
            GetUsersAnswer msg = (GetUsersAnswer) abs_msg;
            this.registered = msg.getUsers();
            fullInfo++;
        }
        else if (abs_msg instanceof GetOnlineUsersAnswer) {
            GetOnlineUsersAnswer msg = (GetOnlineUsersAnswer) abs_msg;
            this.loggedIn = msg.getSet();
            fullInfo++;
        }
        if (fullInfo == 2)
            this.resume();
    }
}