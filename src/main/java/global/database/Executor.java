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
           String[] params,
           ResultHandler<T> handler)
           throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);

        if (params != null) {
            for (int i=0; i < params.length; ++i) {
                stmt.setString(i + 1, params[i]);
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
            String query,
            String... params) throws SQLException {
        try {
            connection.setAutoCommit(false);

            PreparedStatement stmt = connection.prepareStatement(query);

            for (int i=0; i < params.length; ++i) {
                stmt.setString(i + 1, params[i]);
            }

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            if (rowsAffected < 1) {
                throw new SQLException("Update row affected < 1 rows");
            }

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println("Exception during DB execUpdate");
            throw e;
        }
    }
}
