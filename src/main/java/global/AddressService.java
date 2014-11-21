package global;


/**
 * Created by max on 22.11.14.
 */
public class AddressService {

    private static final String SERVLET_ADDRESS = "servlet";
    private static final String DBMAN_ADDRESS = "dbman";
    private static final String MECHANIC_ADDRESS = "gamemech";

    public static String getServletAddr() {
        return SERVLET_ADDRESS;
    }

    public static String getDBManAddr() {
        return DBMAN_ADDRESS;
    }

    public static String getMechanic() {
        return MECHANIC_ADDRESS;
    }
}
