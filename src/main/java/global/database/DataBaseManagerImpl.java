package global.database;

import global.DataBaseManager;
import global.MessageSystem;
import global.database.dao.UsersDAO;
import global.database.dataSets.UsersDataSet;
import global.msgsystem.messages.*;
import global.models.Score;
import global.models.UserSession;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by Евгений on 28.08.2014.
 */
public class DataBaseManagerImpl implements DataBaseManager {

    private Connection conn;
    private static final String DBMAN_ADDRESS = "dbman";
    private final MessageSystem msys;

    public DataBaseManagerImpl(MessageSystem msys, String baseName, String userName, String userPasswd)
            throws SQLException
    {
        this.msys = msys;
        msys.register(this, DBMAN_ADDRESS);

        String baseUrl = "jdbc:mysql://localhost/" + baseName;
        String baseUserName = userName;
        String baseUserPasswd = userPasswd;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.conn = DriverManager.getConnection(baseUrl, baseUserName, baseUserPasswd);
            System.out.println("DB connected");
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
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
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            UsersDataSet user = userDAO.get(login);
            if (user != null) {
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Exception during DB Select in userExists");
        }
        return false;
    }

    @Override
    public void getUsers(){
        String query = "SELECT * FROM User;";
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            ArrayList<UsersDataSet> users = userDAO.getAll();
            this.msys.sendMessage(new GetUsersAnswer(users), "servlet");
        }
        catch (Exception e) {
            System.out.println("Sql exception during getUsers()");
        }
    }

    @Override
    public void checkAuth(UserSession userSession, String passw) {
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            UsersDataSet user = userDAO.get(userSession.getLogin(), passw);

            if (user != null) {
                userSession.setSuccessAuth(true);
                userSession.setUserId(user.getId());
                this.msys.sendMessage(new AuthAnswer(userSession), "servlet");
            }
            else {
                userSession.setSuccessAuth(false);
                this.msys.sendMessage(new AuthAnswer(userSession), "servlet");
            }
        }
        catch (Exception e) {
            System.out.println("Sql exception during checkAuth()");
            userSession.setSuccessAuth(false);
            this.msys.sendMessage(new AuthAnswer(userSession), "servlet");
        }
    }

    @Override
    public void registerUser(String login, String passw) {
        if (this.userExists(login)) {
            this.msys.sendMessage(new RegistrationAnswer(false, "", "User with this login already Exists"), "servlet");
        }
        else {
            try {
                UsersDAO userDAO = new UsersDAO(conn);
                userDAO.add(login, passw);
                this.msys.sendMessage(new RegistrationAnswer(true, login, ""), "servlet");
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println("Exception during DB insert in registration");
                this.msys.sendMessage(new RegistrationAnswer(false, "", "Cannot add User "), "servlet");
            }
        }
    }

    @Override
    public void bestScores() {
        String query = "SELECT login, score FROM User ORDER BY score DESC LIMIT 10;";

        try {
            PreparedStatement statement = this.conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            ArrayList<Score> scores = new ArrayList();

            String login;
            int score;
            while (result.next()) {
                login = result.getString("login");
                score = result.getInt("score");
                scores.add(new Score(login, score));
            }
            this.msys.sendMessage(new BestScoresAnswer(scores), "servlet");
        }
        catch (Exception e) {
            System.out.println("Exception during DB select in bestScores");
        }
    }

    @Override
    public void changePassword(String login, String curPassw, String newPassw) {
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            UsersDataSet user = userDAO.get(login, curPassw);

            if (user != null) {
                userDAO.changePassw(login, newPassw);
                this.msys.sendMessage(new ChangePasswordAnswer(true, ""), "servlet");
            }
            else {
                this.msys.sendMessage(new ChangePasswordAnswer(false, "Wrong current password"), "servlet");
            }
        }
        catch (SQLException e) {
            System.out.println("Sql exception during changePassword()");
            this.msys.sendMessage(new ChangePasswordAnswer(false, "Cannot change password"), "servlet");
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
