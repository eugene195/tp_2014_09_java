package global.database.dao;

import global.database.Executor;
import global.database.handlers.ResultHandler;
import global.database.dataSets.UserDataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO extends AbstractDAO {

    public UsersDAO(Executor exec){
        super(exec);
    }

    public UserDataSet get(String login) throws SQLException {
        String query = "SELECT * FROM User WHERE login=?;";
        ArrayList<UserDataSet> users = getUsers(query, login);

        return (users == null) ? null : users.get(0);
    }

    public UserDataSet get(String login, String passw) throws SQLException {
        String query = "SELECT * FROM User WHERE login=? AND passw=md5(?);";
        ArrayList<UserDataSet> users = getUsers(query, login, passw);

        return (users == null) ? null : users.get(0);
    }

    public ArrayList<UserDataSet> getAll() throws SQLException {
        return getUsers("SELECT * FROM User");
    }

    public ArrayList<UserDataSet> getTop() throws SQLException {
        return getUsers("SELECT * FROM User ORDER BY score DESC LIMIT 10;");
    }

    public void add(String login, String passw) throws SQLException {
        String query = "INSERT INTO User (login, passw)" + " VALUES (?, md5(?));";

        exec.execUpdate(query, login, passw);
    }

    public void changePassw(String login, String passw) throws SQLException {
        String query = "UPDATE User Set passw = md5(?) WHERE login = ?;";

        exec.execUpdate(query, passw, login);
    }

    public void delete(String login) throws SQLException {
        String query = "DELETE FROM User WHERE login = ?;";

        exec.execUpdate(query, login);
    }

    private ArrayList<UserDataSet> getUsers(String query, String... params)
            throws SQLException {

        return exec.execQuery(query,
            params,
            new ResultHandler<UserDataSet>() {

                public ArrayList<UserDataSet> handle(ResultSet result) throws SQLException {
                    ArrayList<UserDataSet> users = new ArrayList();
                    while(result.next()) {
                        users.add(new UserDataSet(result.getLong("userId"),
                                result.getString("login"),
                                result.getString("passw"),
                                result.getInt("score")));
                    }

                    return users;
                }
            });
    }

}
