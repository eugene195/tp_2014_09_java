package global;


/**
 * Created by max on 22.11.14.
 */
public class AddressService {

    private static final String SERVLET_ADDRESS = "servlet";
    private static final String DBMAN_ADDRESS = "dbman";

    private static final String MECHANIC_ADDRESS = "gamemech";
    private static final String SECOND_MECHANIC = "gamemech2";

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
