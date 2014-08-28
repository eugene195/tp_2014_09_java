import java.sql.*;

/**
 * Created by Евгений on 28.08.2014.
 */
public class DataBaseManager {

    private static final String baseUrl = "jdbc:mysql://localhost/java_db";
    private static final String baseUserName = "root";
    private static final String baseUserPasswd = "22061994";

    private Connection conn;

    public DataBaseManager() {
        super();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.conn = DriverManager.getConnection(baseUrl, baseUserName, baseUserPasswd);
            System.out.println("DB connected");
        }
        catch (Exception e) {
            System.err.println ("Cannot connect to DB");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.conn != null) {
            try {
                this.conn.close();
                System.out.println("DB connection terminated");
            }
            catch (Exception e) {
                System.out.println("DB cannot be terminated");
            }
        }
    }

    public ResultSet executeSql(String query) {
        if (this.conn != null) {
            try {
                Statement statement = this.conn.createStatement();
                ResultSet result = statement.executeQuery(query);
                return result;
            }
            catch (SQLException e) {
                System.out.println("sql exception during executeSql");
            }
        }
        return null;
    }

    public void test() {
        ResultSet result = this.executeSql("SELECT * FROM User;");
        try {
            result.next();
            System.out.println(result.get);
        }
        catch (Exception e) {
            System.out.println("Fucking shit");
        }

    }

}
