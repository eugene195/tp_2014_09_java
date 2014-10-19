package global;

/**
 * Created by Moiseev Maxim on 19.10.14.
 */
public interface ResourceFactory {

    public <T extends Object> T get(String resourceName);

    public void loadAllResources();
}
