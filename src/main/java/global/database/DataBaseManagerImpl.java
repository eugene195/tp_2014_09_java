package global.database;

import global.AddressService;
import global.DataBaseManager;
import global.MessageSystem;
import global.database.dao.UsersDAO;
import global.database.dataSets.UserDataSet;
import global.models.Score;
import global.models.UserSession;
import global.msgsystem.messages.toServlet.*;
import snaq.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created by Евгений on 28.08.2014.
 */
public class DataBaseManagerImpl implements DataBaseManager {
    private final MessageSystem msys;
    private Executor executor;
    private final String address;

    // TODO: enum for errors and then map it for client

    public DataBaseManagerImpl(MessageSystem msys, String baseName, String userName, String userPasswd)
            throws SQLException
    {
        this.address = AddressService.registerDBMan();
        this.msys = msys;
        msys.register(this);

        String baseUrl = "jdbc:mysql://localhost/" + baseName;

        try {
            Class c = Class.forName("com.mysql.jdbc.Driver");
            Driver driver = (Driver)c.newInstance();
            DriverManager.registerDriver(driver);

            int minPool = 5, maxPool = 10, maxSize = 30, idleTime = 180;
            ConnectionPool conPool = new ConnectionPool("local",
                    minPool, maxPool, maxSize, idleTime, baseUrl, userName, userPasswd);

            this.executor = new Executor(conPool);
            System.out.println("DB connected");
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new SQLException(e.getCause());
        }
    }

    @Override
    public String getAddress() {
        return this.address;
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
            System.out.println(e.getMessage() + "\n" + "Exception during DB Select in userExists");
        }
        return false;
    }

    @Override
    public void getUsers(String addressTo) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
            ArrayList<UserDataSet> users = userDAO.getAll();
            this.msys.sendMessage(new GetUsersAnswer(address, addressTo, users));
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + "\nSql exception during getUsers()");
        }
    }

    @Override
    public void checkAuth(String addressTo, UserSession userSession, String passw) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
            UserDataSet user = userDAO.get(userSession.getLogin(), passw);

            if (user != null) {
                userSession.setSuccessAuth(true);
                userSession.setUserId(user.getId());
                this.msys.sendMessage(new AuthAnswer(address, addressTo, userSession));
            }
            else {
                userSession.setSuccessAuth(false);
                this.msys.sendMessage(new AuthAnswer(address, addressTo, userSession));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + "\nSql exception during checkAuth()");
            userSession.setSuccessAuth(false);
            this.msys.sendMessage(new AuthAnswer(address, addressTo, userSession));
        }
    }

    @Override
    public void registerUser(String addressTo, String login, String passw) {
        if (userExists(login)) {
            this.msys.sendMessage(new RegistrationAnswer(address, addressTo, false, "", "User with this login already Exists"));
        }
        else {
            try {
                UsersDAO userDAO = new UsersDAO(executor);
                userDAO.add(login, passw);
                this.msys.sendMessage(new RegistrationAnswer(address, addressTo, true, login, ""));
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println("Exception during DB insert in registration");
                this.msys.sendMessage(new RegistrationAnswer(address, addressTo, false, "", "Cannot add User "));
            }
        }
    }


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
            System.out.println("Sql exception during user deletion()");
        }
    }

    @Override
    public void changeScores(String addressTo, Map<String, Integer> extraScoresUsers) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
            userDAO.changeScores(extraScoresUsers);
        }
        catch (SQLException e) {
            System.out.println("Sql exception during user deletion()");
        }
    }

    @Override
    public void bestScores(String addressTo) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
            ArrayList<UserDataSet> users = userDAO.getTopScorers();

            ArrayList<Score> scores = new ArrayList<>();
            for (UserDataSet user: users) {
                scores.add(new Score(user.getLogin(), user.getScore()));
            }

            this.msys.sendMessage(new BestScoresAnswer(address, addressTo, scores));
        }
        catch (Exception e) {
            System.out.println("Exception during DB select in bestScores");
        }
    }

    @Override
    public void changePassword(String addressTo, String login, String curPassw, String newPassw) {
        try {
            UsersDAO userDAO = new UsersDAO(executor);
            UserDataSet user = userDAO.get(login, curPassw);

            if (user != null) {
                userDAO.changePassw(login, newPassw);
                this.msys.sendMessage(new ChangePasswordAnswer(address, addressTo, true, ""));
            }
            else {
                this.msys.sendMessage(new ChangePasswordAnswer(address, addressTo, false, "Wrong current password"));
            }
        }
        catch (SQLException e) {
            System.out.println("Sql exception during changePassword()");
            this.msys.sendMessage(new ChangePasswordAnswer(address, addressTo, false, "Cannot change password"));
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
