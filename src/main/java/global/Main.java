package global;

import global.implementations.DataBaseManagerImpl;
import global.implementations.ServletImpl;
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

    private static final String STATIC_DIR = "public_html";

    private static void configureThreads(Runnable ... tasks) {
        ExecutorService threadPool = Executors.newFixedThreadPool(tasks.length);
        for(Runnable task : tasks)
            threadPool.submit(task);
    }

    private static Server createAndConfigureServer(int serverPort, HandlerList handlers) {
        System.out.append("Starting at port: ").append(String.valueOf(serverPort)).append('\n');
        Server server = new Server(serverPort);
        server.setHandler(handlers);
        return server;
    }

    private static ServletImpl createServlet(MessageSystem msys) {
        return new ServletImpl(msys);
    }

    private static DataBaseManager createDbMan(MessageSystem msys) {
        return new DataBaseManagerImpl(msys);
    }

    private static int getServerPort(String[] args) {
        int SERVER_PORT = 8081;

        if (args.length == 1) {
            String portString = args[0];
            SERVER_PORT = Integer.valueOf(portString);
        }
        return SERVER_PORT;
    }

    private static ServletContextHandler createContext(ServletImpl servletImpl) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(servletImpl), "/");
        return context;
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
        MessageSystem msys = new MessageSystem();
        ServletImpl servletImpl = createServlet(msys);
        DataBaseManager dbman = createDbMan(msys);

        configureThreads(servletImpl, dbman);

        ServletContextHandler context = createContext(servletImpl);
        HandlerList handlers = makeServerHandlers();
        handlers.addHandler(context);

        int serverPort = getServerPort(args);
        Server server = createAndConfigureServer(serverPort, handlers);
        server.start();
        server.join();
    }
}
