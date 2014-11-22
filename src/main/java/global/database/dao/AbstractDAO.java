package global.database.dao;

import global.database.Executor;

/**
 * Created by max on 15.11.14.
 */
public class AbstractDAO {
    protected Executor exec;

    public AbstractDAO(Executor exec) {
        this.exec = exec;
    }
}
