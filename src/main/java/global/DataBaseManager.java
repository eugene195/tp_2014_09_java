package global;

import global.messages.CheckedAuthMsg;

import java.sql.*;

import static java.lang.Thread.sleep;

/**
 * Created by Евгений on 28.08.2014.
 */
public class DataBaseManager implements Runnable {

    private static final String baseUrl = "jdbc:mysql://localhost/java_db";
    private static final String baseUserName = "root";
    private static final String baseUserPasswd = "22061994";

    private Connection conn;
    private static final String DBMAN_ADDRESS = "dbman";
    private final MessageSystem msys;

    public DataBaseManager(MessageSystem msys) {
        super();

        this.msys = msys;
        msys.register(this, DBMAN_ADDRESS);

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

    private int getResultCount(ResultSet resultSet) throws SQLException {
        resultSet.last();
        int count = resultSet.getRow();
        resultSet.beforeFirst();
        return count;
    }

    public void checkAuth(String login, String passw) {
        String query = "SELECT * FROM User WHERE `login`='" + login + "' AND `passw`=md5('"+ passw + "');";
        ResultSet result = this.executeSql(query);

        try {

            while (result.next()) {
                System.out.println(result.getString("login"));
            }

            if (this.getResultCount(result) == 1) {
                result.next();
                this.msys.sendMessage(new CheckedAuthMsg(true, login), "servlet");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("Sql exception during checkAuth()");
        }

        this.msys.sendMessage(new CheckedAuthMsg(false, ""), "servlet");
    }

    public void test() {
        ResultSet result = this.executeSql("SELECT * FROM User;");
        try {

            while (result.next()) {
                System.out.println(result.getString("login"));
            }
        }
        catch (Exception e) {
            System.out.println("Fucking shit");
        }

    }

    @Override
    public void run() {
        while (true) {
            this.msys.executeFor(this);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
