import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    private static Servlet configure() {
        final int THREADS_AMOUNT = 1;

        MessageSystem msys = new MessageSystem();
        Servlet servlet = new Servlet(msys);

        ExecutorService threadPool = Executors.newFixedThreadPool(THREADS_AMOUNT);
        threadPool.submit(servlet);

        return servlet;
    }

    public static void main(String[] args) throws Exception
    {
        Servlet servlet = configure();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(servlet), "/*");

        HandlerList handlers = new HandlerList();
        handlers.addHandler(context);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
