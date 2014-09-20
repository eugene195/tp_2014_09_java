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
    private static final String STATIC_DIR = "src/main/static";
    private static final String MAIN_PAGE_ADDRESS = "/auth";

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

        //переадресация пользователя с / на нужный нам адрес
        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(true);
        rewriteHandler.setOriginalPathAttribute("requestedPath");

        RedirectRegexRule rule = new RedirectRegexRule();
        rule.setRegex("/");
        rule.setReplacement(MAIN_PAGE_ADDRESS);
        rewriteHandler.addRule(rule);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {rewriteHandler, resourceHandler});
        return handlers;
    }

    public static void main(String[] args) throws Exception {
        final int SERVER_PORT = 8081;




        int port = 8080;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        Servlet servlet = configure();
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.addServlet(new ServletHolder(servlet), "/api/v1/auth/signin");         FRONTEND REALIZATION
        context.addServlet(new ServletHolder(servlet), "/");
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("frontend-stub/public_html");

//        HandlerList handlers = new HandlerList();        FRONTEND REALIZATION
        HandlerList handlers = makeServerHandlers();
        handlers.addHandler(context);
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
