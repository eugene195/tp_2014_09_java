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
public class DataBaseManagerImpl implements global.base.DataBaseManager {

    private static final String baseUrl = "jdbc:mysql://localhost/g01_java_db";
    private static final String baseUserName = "g01_user";
    private static final String baseUserPasswd = "drovosek";

    private Connection conn;
    private static final String DBMAN_ADDRESS = "dbman";
    private final MessageSystem msys;

    public DataBaseManagerImpl(MessageSystem msys) {

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

    public boolean userExists(String login){
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

    @Override
    public boolean getUsers(){
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
            return true;
        }
        catch (Exception e) {
            System.out.println("Sql exception during getUsers()");
            return false;
        }
    }

    @Override
    public boolean checkAuth(UserSession userSession, String passw) {
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
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Sql exception during checkAuth()");
            userSession.setSuccessAuth(false);
            this.msys.sendMessage(new AuthAnswer(userSession), "servlet");
            return false;
        }
    }

    public boolean deleteUser(String login) {
        if (this.userExists(login)) {
            String query = "DELETE FROM User WHERE login = ?;";
            try {
                PreparedStatement statement = this.conn.prepareStatement(query);
                statement.setString(1, login);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated != 1) {
                    System.out.print("Delete in SQL affected more/less than one row");
                    return false;
                }
                return true;
            } catch (SQLException exc) {
                exc.printStackTrace();
                System.out.print("SQL exception during delete");
            }
            return true;
        }
        else {
            System.out.print("User does not exist");
            return false;
        }
    }

    @Override
    public boolean registerUser(String login, String passw) {
        if (this.userExists(login)) {
            this.msys.sendMessage(new RegistrationAnswer(false, "", "User with this login already Exists"), "servlet");
            System.out.println("User with this login already Exists");
            return false;
        }

        String query = "INSERT INTO User (login, passw, score)" + " VALUES (?, md5(?), 0);";
        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, passw);

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected < 1) {
                System.out.println("Smth bad happened. Insert affected < 1 rows");
                this.msys.sendMessage(new RegistrationAnswer(false, "", "SQL Insert error"), "servlet");
            }
            this.msys.sendMessage(new RegistrationAnswer(true, login, ""), "servlet");
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception during DB insert  in registration");
            return false;
        }
    }

    @Override
    public boolean bestScores() {
        String query = "SELECT login, score FROM User ORDER BY score DESC LIMIT 10;";

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
            return true;
        } catch (Exception e) {
            System.out.println("Exception during DB select in bestScores");
            return false;
        }
    }

    @Override
    public boolean changePassword(String login, String curPassw, String newPassw) {
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
                    return false;
                }
                this.msys.sendMessage(new ChangePasswordAnswer(true, ""), "servlet");
                return true;
            }
        }
        catch (Exception e) {
            System.out.println("Sql exception during changePassword()");
        }
        this.msys.sendMessage(new ChangePasswordAnswer(false, "Wrong current password"), "servlet");
        return false;
    }

    @Override
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
