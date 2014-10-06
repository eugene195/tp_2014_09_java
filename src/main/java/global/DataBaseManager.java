package global;

import global.messages.*;
import global.models.Score;
import global.models.UserSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    private boolean userExists(String login){
        String query = "SELECT * FROM User WHERE login=?;";
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, login);

            ResultSet result = statement.executeQuery();
            if (getResultCount(result) >= 1) {
                return true;
            }
        }
        catch (Exception e){
            System.out.println("Exception during DB Select in registration");
        }
        return false;
    }

    public void getUsers(){
        String query = "SELECT * FROM User;";
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            int rows = getResultCount(result);
            HashMap<String, Long> map = new HashMap<>();
            while(rows > 0) {
                result.next();
                Long userId = result.getLong("userId");
                String username = result.getString("login");
                map.put(username, userId);
                rows--;
            }
            this.msys.sendMessage(new GetUsersAnswer(map), "servlet");
            return;
        }
        catch (Exception e) {
            System.out.println("Sql exception during checkAuth()");
        }
    }

    public void checkAuth(UserSession userSession, String passw) {
        String query = "SELECT * FROM User WHERE login=? AND passw=md5(?);";

        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, userSession.getLogin());
            statement.setString(2, passw);
            ResultSet result = statement.executeQuery();

            if (getResultCount(result) == 1) {
                result.next();
                long userId = result.getLong("userId");

                userSession.setSuccessAuth(true);
                userSession.setUserId(userId);
                this.msys.sendMessage(new AuthAnswer(userSession), "servlet");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("Sql exception during checkAuth()");
        }

        userSession.setSuccessAuth(false);
        this.msys.sendMessage(new AuthAnswer(userSession), "servlet");
    }

    public void registerUser(String login, String passw){
        if (this.userExists(login)) {
            this.msys.sendMessage(new RegistrationAnswer(false, "", "User with this login already userExists"), "servlet");
            return;
        }

        String query = "INSERT INTO User (login, passw)" + " VALUES (?, md5(?));";
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, passw);

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected < 1) {
                System.out.println("Smth bad happened. Insert affected < 1 rows");
                this.msys.sendMessage(new RegistrationAnswer(false, "", "SQL Insert error"), "servlet");
            }
        }
        catch (Exception e){
            System.out.println("Exception during DB insert  in registration");
        }
        this.msys.sendMessage(new RegistrationAnswer(true, login, ""), "servlet");
    }


    public void bestScores() {
        String query = "SELECT login, score ORDER BY score DESC FROM User LIMIT 10;";

        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            ArrayList<Score> scores = new ArrayList<>();

            String login;
            int score;
            while (result.next()) {
                login = result.getString("login");
                score = result.getInt("score");
                scores.add(new Score(login, score));
            }
            this.msys.sendMessage(new BestScoresAnswer(scores), "servlet");
        } catch (Exception e) {
            System.out.println("Exception during DB insert  in registration");
        }
    }


    public void changePassword(String login, String curPassw, String newPassw) {
        String query = "SELECT * FROM User WHERE login=? AND passw=md5(?);";
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, curPassw);
            ResultSet result = statement.executeQuery();

            if (getResultCount(result) == 1) {
                query = "UPDATE User Set passw = md5(?) WHERE login = ?;";
                statement = this.conn.prepareStatement(query);
                statement.setString(1, newPassw);
                statement.setString(2, login);

                int rowsAffected = statement.executeUpdate();
                if(rowsAffected < 1) {
                    System.out.println("Smth bad happened. Update password affected < 1 rows");
                    this.msys.sendMessage(new ChangePasswordAnswer(false, "Failed to change password"), "servlet");
                    return;
                }
                this.msys.sendMessage(new ChangePasswordAnswer(true, ""), "servlet");
                return;
            }

        }
        catch (Exception e) {
            System.out.println("Sql exception during changePassword()");
        }

        this.msys.sendMessage(new ChangePasswordAnswer(false, "Wrong current password"), "servlet");
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
