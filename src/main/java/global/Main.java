package global;

import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

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
    private static final int DEFAULT_SERVER_PORT = 8081;

    /**
     * Создание объекта сервера.
     * Внутри осуществляется выбор номера порта.
     * @param args параметры запуска приложения.
     * @return объект Server.
     */
    private static Server makeServer(String args[]) {
        //проверяем наличие параметра порт.
        // Если не передан - запускаемся на порту по умолчанию(переменная DEFAULT_SERVER_PORT).
        int port;
        if (args.length < 1) {
            System.out.append("No port in parametrs. Using default port " + DEFAULT_SERVER_PORT + ".\n");
            port = DEFAULT_SERVER_PORT;
        } else {
            String portString = args[0];
            port = Integer.valueOf(portString);
            System.out.append("Starting at port: ").append(portString).append('\n');
        }
        return new Server(port);
    }

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
        Servlet servlet = configure();
        Server server = makeServer(args);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(servlet), "/");

        HandlerList handlers = makeServerHandlers();

        handlers.addHandler(context);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
