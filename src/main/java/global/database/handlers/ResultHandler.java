package global.database.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ResultHandler<T> {
    ArrayList<T> handle(ResultSet result) throws SQLException;
}
