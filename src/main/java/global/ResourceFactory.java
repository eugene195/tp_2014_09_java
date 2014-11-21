package global;

/**
 * Created by Moiseev Maxim on 19.10.14.
 */
public interface ResourceFactory {
    String RESOURCE_ROOT = "data";

    <ResourceType extends Object> ResourceType get(String resourceName);
    void loadAllResources(String root);
}
