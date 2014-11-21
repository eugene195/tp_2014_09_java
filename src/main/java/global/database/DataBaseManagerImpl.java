package global.database;

import global.AddressService;
import global.DataBaseManager;
import global.MessageSystem;
import global.database.dao.UsersDAO;
import global.database.dataSets.UserDataSet;
import global.msgsystem.messages.*;
import global.models.Score;
import global.models.UserSession;
import snaq.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by Евгений on 28.08.2014.
 */
public class DataBaseManagerImpl implements DataBaseManager {
    private final MessageSystem msys;
    private Executor executor;

    public DataBaseManagerImpl(MessageSystem msys, String baseName, String userName, String userPasswd)
            throws SQLException
    {
        this.msys = msys;
        msys.register(this, AddressService.getDBManAddr());

        String baseUrl = "jdbc:mysql://localhost/" + baseName;
        String baseUserName = userName;
        String baseUserPasswd = userPasswd;

        try {
            Class c = Class.forName("com.mysql.jdbc.Driver");
            Driver driver = (Driver)c.newInstance();
            DriverManager.registerDriver(driver);

            ConnectionPool conPool = new ConnectionPool("local",
                    5, 10, 30, 180, baseUrl, baseUserName, baseUserPasswd);

            this.executor = new Executor(conPool);
            System.out.println("DB connected");
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new SQLException(e.getCause());
        }

    }

    @Override
    protected void finalize() throws Throwable {
        try {
            this.executor.releaseConnectionPool();
            System.out.println("DB connection terminated");
        }
        catch (Exception e) {
            System.out.println("DB cannot be terminated");
            }
    }

    public boolean userExists(String login){
        try {
            UsersDAO userDAO = new UsersDAO(executor);
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
            UsersDAO userDAO = new UsersDAO(executor);
            ArrayList<UserDataSet> users = userDAO.getAll();
            this.msys.sendMessage(new GetUsersAnswer(users), AddressService.getServletAddr());
        }
        catch (Exception e) {
            System.out.println("Sql exception during getUsers()");
        }
    }

    @Override
    public void checkAuth(UserSession userSession, String passw) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
            UserDataSet user = userDAO.get(userSession.getLogin(), passw);

            if (user != null) {
                userSession.setSuccessAuth(true);
                userSession.setUserId(user.getId());
                this.msys.sendMessage(new AuthAnswer(userSession), AddressService.getServletAddr());
            }
            else {
                userSession.setSuccessAuth(false);
                this.msys.sendMessage(new AuthAnswer(userSession), AddressService.getServletAddr());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Sql exception during checkAuth()");
            userSession.setSuccessAuth(false);
            this.msys.sendMessage(new AuthAnswer(userSession), AddressService.getServletAddr());
        }
    }

    @Override
    public void registerUser(String login, String passw) {
        if (this.userExists(login)) {
            this.msys.sendMessage(new RegistrationAnswer(false, "", "User with this login already Exists"), AddressService.getServletAddr());
        }
        else {
            try {
                UsersDAO userDAO = new UsersDAO(executor);
                userDAO.add(login, passw);
                this.msys.sendMessage(new RegistrationAnswer(true, login, ""), AddressService.getServletAddr());
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println("Exception during DB insert in registration");
                this.msys.sendMessage(new RegistrationAnswer(false, "", "Cannot add User "), AddressService.getServletAddr());
            }
        }
    }

    @Override
    public void deleteUser(String login) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
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
            UsersDAO userDAO = new UsersDAO(executor);
            ArrayList<UserDataSet> users = userDAO.getTop();

            ArrayList<Score> scores = new ArrayList();
            for (UserDataSet user: users) {
                scores.add(new Score(user.getLogin(), user.getScore()));
            }

            this.msys.sendMessage(new BestScoresAnswer(scores), AddressService.getServletAddr());
        }
        catch (Exception e) {
            System.out.println("Exception during DB select in bestScores");
        }
    }

    @Override
    public void changePassword(String login, String curPassw, String newPassw) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
            UserDataSet user = userDAO.get(login, curPassw);

            if (user != null) {
                userDAO.changePassw(login, newPassw);
                this.msys.sendMessage(new ChangePasswordAnswer(true, ""), AddressService.getServletAddr());
            }
            else {
                this.msys.sendMessage(new ChangePasswordAnswer(false, "Wrong current password"), AddressService.getServletAddr());
            }
        }
        catch (SQLException e) {
            System.out.println("Sql exception during changePassword()");
            this.msys.sendMessage(new ChangePasswordAnswer(false, "Cannot change password"), AddressService.getServletAddr());
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
