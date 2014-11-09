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

    private UsersDataSet getUsersDataSet(ResultSet result) throws SQLException {
        return new UsersDataSet(result.getLong(1),
                result.getString(2),
                result.getString(3),
                result.getString(4));
    }

    public UsersDataSet get(String login) throws SQLException {
        Executor exec = new Executor();
        ArrayList<String> params = new ArrayList<String>();
        params.add(login);
        ArrayList<UsersDataSet> users = exec.execQuery(con, "SELECT * FROM User WHERE login=?;",
            params,
            new ResultHandler<UsersDataSet>() {

            public ArrayList<UsersDataSet> handle(ResultSet result) throws SQLException {
                ArrayList<UsersDataSet> users = new ArrayList<UsersDataSet>();
                result.next();
                users.add(getUsersDataSet(result));

                return users;
            }

        });
        return (users == null) ? null : users.get(0);
    }

    public ArrayList<UsersDataSet> getAll() throws SQLException {
        Executor exec = new Executor();
        return exec.execQuery(con, "SELECT * FROM User",
            null,
            new ResultHandler<UsersDataSet>() {

                public ArrayList<UsersDataSet> handle(ResultSet result) throws SQLException {
                    ArrayList<UsersDataSet> users = new ArrayList<UsersDataSet>();
                    while(result.next()) {
                        users.add(getUsersDataSet(result));
                    }

                    return users;
                }
            });
    }
}
