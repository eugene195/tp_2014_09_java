package global;

/**
 * Created by Moiseev Maxim on 19.10.14.
 */
public interface ResourceFactory {

    public <ResourceType extends Object> ResourceType get(String resourceName);

    public void loadAllResources();
}
