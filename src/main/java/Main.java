import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Servlet()), "/*");

        HandlerList handlers = new HandlerList();
        handlers.addHandler(context);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
