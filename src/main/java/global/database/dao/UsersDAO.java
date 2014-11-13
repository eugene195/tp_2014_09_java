package global.database.dao;

import global.database.Executor;
import global.database.handlers.ResultHandler;
import global.database.dataSets.UsersDataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO {
    private Connection con;

    public UsersDAO(Connection con){
        this.con = con;
    }

    public UsersDataSet get(String login) throws SQLException {
        String query = "SELECT * FROM User WHERE login=?;";
        ArrayList<UsersDataSet> users = getUsers(query, login);

        return (users == null) ? null : users.get(0);
    }

    public UsersDataSet get(String login, String passw) throws SQLException {
        String query = "SELECT * FROM User WHERE login=? AND passw=md5(?);";
        ArrayList<UsersDataSet> users = getUsers(query, login, passw);

        return (users == null) ? null : users.get(0);
    }

    public ArrayList<UsersDataSet> getAll() throws SQLException {
        return getUsers("SELECT * FROM User", null);
    }

    public ArrayList<UsersDataSet> getTop() throws SQLException {
        return getUsers("SELECT * FROM User ORDER BY score DESC LIMIT 10;", null);
    }

    public void add(String login, String passw) throws SQLException {
        Executor exec = new Executor();
        String query = "INSERT INTO User (login, passw)" + " VALUES (?, md5(?));";

        exec.execUpdate(con, query, login, passw);
    }

    public void changePassw(String login, String passw) throws SQLException {
        Executor exec = new Executor();
        String query = "UPDATE User Set passw = md5(?) WHERE login = ?;";

        exec.execUpdate(con, query, passw, login);
    }

    private ArrayList<UsersDataSet> getUsers(String query,
            String... params) throws SQLException {

        Executor exec = new Executor();
        return exec.execQuery(con, query,
            params,
            new ResultHandler<UsersDataSet>() {

                public ArrayList<UsersDataSet> handle(ResultSet result) throws SQLException {
                    ArrayList<UsersDataSet> users = new ArrayList<UsersDataSet>();
                    while(result.next()) {
                        users.add(new UsersDataSet(result.getLong("userId"),
                                result.getString("login"),
                                result.getString("passw"),
                                result.getInt("score")));
                    }

                    return users;
                }
            });
    }

}
