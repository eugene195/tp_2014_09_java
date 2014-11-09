package global.database;

import global.database.handlers.ResultHandler;

import java.sql.*;
import java.util.ArrayList;


public class Executor {
    private static int getResultCount(ResultSet resultSet) throws SQLException {
        resultSet.last();
        int count = resultSet.getRow();
        resultSet.beforeFirst();
        return count;
    }

    public <T> ArrayList<T> execQuery(Connection connection,
           String query,
           ArrayList<String> params,
           ResultHandler<T> handler)
           throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);

        if (params != null) {
            for (int i=0; i < params.size(); ++i) {
                stmt.setString(i + 1, params.get(i));
            }
        }

        stmt.executeQuery();
        ResultSet result = stmt.getResultSet();

        ArrayList<T> value = null;
        if (getResultCount(result) >= 1) {
            value = handler.handle(result);
        }

        result.close();
        stmt.close();

        return value;
    }

    public void execUpdate(Connection connection,
            String[] updates,
            ArrayList<String> params) {
        try {
            connection.setAutoCommit(false);
            for(String update: updates){
                PreparedStatement stmt = connection.prepareStatement(update);

                for (int i=0; i < params.size(); ++i) {
                    stmt.setString(i, params.get(i));
                }

                stmt.executeUpdate(update);
                stmt.close();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }
}
