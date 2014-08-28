import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Евгений on 28.08.2014.
 */
public class DataBaseManager {

//
//    Нужны методы Get и Set
//
    private final String baseUrl = "jdbc:mysql://localhost/java_db";
    private final String baseUserName = "root";
    private final String baseUserPasswd = "";

    public void executeSql(String query){
        Connection conn = null;

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(baseUrl, baseUserName, baseUserPasswd);
            System.out.println("DB connected");
        }
        catch (Exception e){
            System.err.println ("Cannot connect to DB");
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("DB connection terminated");
                }
                catch (Exception e) {

                }
            }
        }


    }

}
