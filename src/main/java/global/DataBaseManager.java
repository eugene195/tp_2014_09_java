package global;

import global.messages.AuthAnswer;
import global.messages.ProfileInfoAnswer;
import global.messages.RegistrationAnswer;

import java.sql.*;
import java.util.Map;

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

    private static int getResultCount(ResultSet resultSet) throws SQLException {
        resultSet.last();
        int count = resultSet.getRow();
        resultSet.beforeFirst();
        return count;
    }

    private boolean exists(String login){
        PreparedStatement statement = null;
        ResultSet result = null;

        String query = "SELECT * FROM User WHERE login=?;";
        try {
            statement = this.conn.prepareStatement(query);
            statement.setString(1, login);
            result = statement.executeQuery();
            if (getResultCount(result) >= 1) {
                return true;
            }
        }
        catch (Exception e){
            System.out.println("Exception during DB Select in registration");
        }
        return false;
    }

    public void checkAuth(String login, String passw) {
        String query = "SELECT * FROM User WHERE login=? AND passw=md5(?);";
        ResultSet result = null;
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, passw);
            result = statement.executeQuery();
        }
        catch (Exception e){
            System.out.println("Sql exception during checkAuth()");
        }

        try {
            if (getResultCount(result) == 1) {
                result.next();
                long userId = result.getLong("userId");
                this.msys.sendMessage(new AuthAnswer(true, login, userId), "servlet");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("Sql exception during checkAuth()");
        }

        this.msys.sendMessage(new AuthAnswer(false, "", -1), "servlet");
    }

    public void registerUser(String login, String passw){
        PreparedStatement statement = null;
        ResultSet result = null;
        String query = null;

        if (this.exists(login)) {
            this.msys.sendMessage(new RegistrationAnswer(false, "", "User with this login already exists"), "servlet");
            return;
        }

        query = "INSERT INTO User (login, passw)" + " VALUES (?, md5(?));";
        try {
            statement = this.conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, passw);

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected < 1){
                System.out.println("Smth bad happened. Insert affected < 1 rows");
                this.msys.sendMessage(new RegistrationAnswer(false, "", "SQL Insert error"), "servlet");
            }
        }
        catch (Exception e){
            System.out.println("Exception during DB insert  in registration");
        }
        this.msys.sendMessage(new RegistrationAnswer(true, login, ""), "servlet");
    }

    public void getProfileInfo(long userId) {
        this.msys.sendMessage(new ProfileInfoAnswer(), "servlet");
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
