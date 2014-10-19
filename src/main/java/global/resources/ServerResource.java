package global.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moiseev Maxim on 18.10.14.
 */
public class ServerResource implements Resource {
    private final Map<String, Integer> serverConfiguration = new HashMap<>();

    public Integer getServerPort() {
        Integer serverPort = serverConfiguration.get("serverPort");
        if (serverPort == null) {
            throw new RuntimeException("Server port not found in ServerResource");
        }
        return serverPort;
    }

}
