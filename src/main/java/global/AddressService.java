package global;


import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by max on 22.11.14.
 */
public class AddressService {

    private static final AtomicLong idCounter = new AtomicLong();

    private static final String SERVLET_PREFIX = "servlet";
    private static final String DBMAN_PREFIX = "dbman";
    private static final String MECHANIC_PREFIX = "gamemech";

    private static String SERVLET_ADDRESS;
    private static String DBMAN_ADDRESS;
    private static String MECHANIC_ADDRESS;

    // private static String SECOND_MECHANIC;

    public static String registerServlet() {
        SERVLET_ADDRESS = SERVLET_PREFIX + idCounter.getAndIncrement();
        return SERVLET_ADDRESS;
    }

    public static String registerDBMan() {
        DBMAN_ADDRESS = DBMAN_PREFIX + idCounter.getAndIncrement();
        return DBMAN_ADDRESS;
    }

    public static String registerMechanic() {
        MECHANIC_ADDRESS = MECHANIC_PREFIX + idCounter.getAndIncrement();
        return MECHANIC_ADDRESS;
    }

    public static String getServletAddr() {
        return SERVLET_ADDRESS;
    }

    public static String getDBManAddr() {
        return DBMAN_ADDRESS;
    }

    public static String getMechanic() {
        return MECHANIC_ADDRESS;
        // or return SECOND_MECHANIC, if the first one is overloaded with requests
    }
}
