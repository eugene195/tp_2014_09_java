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
    private static final String STATIC_DIR = "src/main/static/";
    private static final String MAIN_PAGE_ADDRESS = "/main";

    private static Servlet configure() {
        final int THREADS_AMOUNT = 1;

        MessageSystem msys = new MessageSystem();
        Servlet servlet = new Servlet(msys);

        ExecutorService threadPool = Executors.newFixedThreadPool(THREADS_AMOUNT);
        threadPool.submit(servlet);

        return servlet;
    }

    /**
     * Создание и настройка обработчиков для внутренних нужд сервера.
     * @return Список обработчиков.
     */
    private static HandlerList makeServerHandlers() {
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(false); // не показывать содержание директории при переходе по /
        resource_handler.setResourceBase(STATIC_DIR); //путь к папке статики от корня проекта

        //переадресация пользователя с / на нужный нам адрес
        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(true);
        rewriteHandler.setOriginalPathAttribute("requestedPath");

        RedirectRegexRule rule = new RedirectRegexRule();
        rule.setRegex("/");  //здесь устанавливаем откуда перенаправлять
        rule.setReplacement(MAIN_PAGE_ADDRESS);  //а здесь - куда
        rewriteHandler.addRule(rule);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {rewriteHandler, resource_handler});
        return handlers;
    }

    public static void main(String[] args) throws Exception {
        final int SERVER_PORT = 8081;

        Servlet servlet = configure();
        Server server = new Server(SERVER_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(servlet), "/*");

        HandlerList handlers = makeServerHandlers();

        handlers.addHandler(context);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
