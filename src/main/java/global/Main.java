package global;

import global.database.DataBaseManagerImpl;
import global.msgsystem.MessageSystemImpl;
import global.resources.ResourceFactoryImpl;
import global.servlet.ServletImpl;
import global.resources.ServerResource;

import global.mechanic.GameMechanicsImpl;
import global.mechanic.sockets.WebSocketGameServlet;
import global.mechanic.sockets.WebSocketServiceImpl;

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
            ResourceFactory resourceFactory = createRFactory();
            ServerResource conf = resourceFactory.get(SERVER_CONFIG);

            MessageSystem msys = new MessageSystemImpl();
            ServletImpl servletImpl = createServlet(msys);
            DataBaseManager dbman = createDbMan(msys, conf);

            WebSocketService webSocketService = new WebSocketServiceImpl();
            GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

            configureThreads(servletImpl, dbman, gameMechanics);

            ServletContextHandler context = createContext(servletImpl, webSocketService, gameMechanics);
            HandlerList handlers = createServerHandlers();
            handlers.addHandler(context);

            int serverPort = conf.getServerPort();

            Server server = createServer(serverPort);
            server.setHandler(handlers);
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

    private static ResourceFactory createRFactory() {
        ResourceFactory resourceFactory = ResourceFactoryImpl.getInstance();
        resourceFactory.loadAllResources(ResourceFactory.RESOURCE_ROOT);
        return resourceFactory;
    }

    private static Server createServer(int serverPort) {
        System.out.println("Starting at port: " + String.valueOf(serverPort));
        Server server = new Server(serverPort);
        return server;
    }

    private static ServletImpl createServlet(MessageSystem msys) {
        return new ServletImpl(msys);
    }

    private static DataBaseManager createDbMan(MessageSystem msys, ServerResource conf)
        throws SQLException
    {
        String database = conf.getDatabase();
        String dbuser = conf.getDbuser();
        return new DataBaseManagerImpl(msys, database, dbuser, "drovosek");
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
    private static HandlerList createServerHandlers() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false); // не показывать содержание директории при переходе по /
        resourceHandler.setResourceBase(STATIC_DIR); //путь к папке статики от корня проекта

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler});

        return handlers;
    }
}
