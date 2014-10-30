package global;


import global.database.DataBaseManagerImpl;
import global.implementations.GameMechanicsImpl;
import global.msgsystem.MessageSystem;
import global.msgsystem.MessageSystemImpl;
import global.resources.ResourceFactoryImpl;
import global.servlet.ServletImpl;
import global.resources.ServerResource;
import global.sockets.WebSocketGameServlet;
import global.sockets.WebSocketServiceImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{

    private static final String STATIC_DIR = "public_html";
    private static final String SERVER_CONFIG = "ServerConfig.xml";

    public static void main(String[] args) throws Exception {
        try {
            MessageSystem msys = new MessageSystemImpl();
            ServletImpl servletImpl = createServlet(msys);
            DataBaseManager dbman = createDbMan(msys);
            WebSocketService webSocketService = new WebSocketServiceImpl();
            GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

            configureThreads(servletImpl, dbman, gameMechanics);
            ServletContextHandler context = createContext(servletImpl, webSocketService, gameMechanics);

            HandlerList handlers = makeServerHandlers();
            handlers.addHandler(context);

            ResourceFactory resourceFactory = ResourceFactoryImpl.getInstance();
            resourceFactory.loadAllResources(ResourceFactory.RESOURCE_ROOT);
            ServerResource serverResource = resourceFactory.get(SERVER_CONFIG);
            int serverPort = serverResource.getServerPort();

            Server server = createAndConfigureServer(serverPort, handlers);
            server.start();
            server.join();
        }
        catch (SQLException e) {
            System.out.println("Cannot connect to DB");
        }
    }

    private static void configureThreads(Runnable ... tasks) {
        ExecutorService threadPool = Executors.newFixedThreadPool(tasks.length);
        for (Runnable task : tasks)
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

    private static DataBaseManager createDbMan(MessageSystem msys)
        throws SQLException
    {
        return new DataBaseManagerImpl(msys, "g01_java_db", "g01_user", "drovosek");
    }

    private static ServletContextHandler createContext( ServletImpl servletImpl,
                                                       WebSocketService webSocketService,
                                                       GameMechanics gameMechanics )
    {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new WebSocketGameServlet(gameMechanics, webSocketService)), "/gameplay");
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
}
