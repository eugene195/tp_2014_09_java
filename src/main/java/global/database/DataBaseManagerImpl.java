package global.database;

import global.DataBaseManager;
import global.MessageSystem;
import global.database.dao.UsersDAO;
import global.database.dataSets.UserDataSet;
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

    public boolean userExists(String login){
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            UserDataSet user = userDAO.get(login);
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
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            ArrayList<UserDataSet> users = userDAO.getAll();
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
            UserDataSet user = userDAO.get(userSession.getLogin(), passw);

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
    public void deleteUser(String login) {
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            UserDataSet user = userDAO.get(login);

            if (user != null) {
                userDAO.delete(login);
            } else {
                System.out.println("Error in deleteUser; User does not exist");
            }
        }
        catch (SQLException e) {
            System.out.println("Sql exception during changePassword()");
        }
    }

    @Override
    public void bestScores() {
        try {
            UsersDAO userDAO = new UsersDAO(conn);
            ArrayList<UserDataSet> users = userDAO.getTop();

            ArrayList<Score> scores = new ArrayList();
            for (UserDataSet user: users) {
                scores.add(new Score(user.getLogin(), user.getScore()));
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
            UserDataSet user = userDAO.get(login, curPassw);

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
