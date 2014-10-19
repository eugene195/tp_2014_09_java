package global.resources;

import java.io.Serializable;

/**
 * Created by Moiseev Maxim on 18.10.14.
 */
public class ServerResource implements Resource {
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
