package global;

import global.DataBaseManager;
import global.MessageSystem;
import global.Servlet;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    private static final String STATIC_DIR = "public_html";

    private static Servlet configure() {
        final int THREADS_AMOUNT = 2;

        MessageSystem msys = new MessageSystem();
        Servlet servlet = new Servlet(msys);
        DataBaseManager dbman = new DataBaseManager(msys);

        ExecutorService threadPool = Executors.newFixedThreadPool(THREADS_AMOUNT);
        threadPool.submit(servlet);
        threadPool.submit(dbman);

        return servlet;
    }

    /**
     * Создание и настройка обработчиков для внутренних нужд сервера.
     * @return Список обработчиков.
     */
    private static HandlerList makeServerHandlers() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false); // не показывать содержание директории при переходе по /
        resourceHandler.setResourceBase(STATIC_DIR); //путь к папке статики от корня проекта

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler});

        return handlers;
    }

    public static void main(String[] args) throws Exception {
        int SERVER_PORT = 8081;

        if (args.length == 1) {
            String portString = args[0];
            SERVER_PORT = Integer.valueOf(portString);
        }

        System.out.append("Starting at port: ").append(String.valueOf(SERVER_PORT)).append('\n');


        Servlet frontend = configure();

        Server server = new Server(SERVER_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/");

        HandlerList handlers = makeServerHandlers();

        handlers.addHandler(context);

        server.setHandler(handlers);

        server.start();
        server.join();

    }
}
