package global;

/**
 * Created by Moiseev Maxim on 19.10.14.
 */
public interface ResourceFactory {
    String RESOURCE_ROOT = "data";

    public <ResourceType extends Object> ResourceType get(String resourceName);

    public void loadAllResources(String root);
}
