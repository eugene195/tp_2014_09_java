package global.resources;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moiseev Maxim on 18.10.14.
 */
public class ServerResource implements Resource, Serializable {
    int port;

    public ServerResource() {
        this.port = 8080;
    }

    public ServerResource(int serverPort) {
        this.port = serverPort;
    }

    public int getServerPort() {
        return port;
    }

}
