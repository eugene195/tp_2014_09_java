package global.resources;

import java.io.Serializable;

/**
 * Created by Moiseev Maxim on 18.10.14.
 */
public class ServerResource implements Resource {
    int port;
    String database;

    public ServerResource() {
        this.port = 8080;
    }

    public int getServerPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

}
