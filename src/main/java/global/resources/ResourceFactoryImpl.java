package global.resources;

import global.ResourceFactory;
import global.resources.sax.ReadXMLFileSAX;
import global.resources.vfs.VFS;
import global.resources.vfs.VFSImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Moiseev Maxim on 18.10.14.
 */
public class ResourceFactoryImpl implements ResourceFactory {
    public static final String RESOURCE_ROOT = "data";
    private static ResourceFactoryImpl instance;
    private static Map<String, Object> resources = new HashMap<>();
    private static VFS vfs;

    private ResourceFactoryImpl() {

    }

    public static ResourceFactoryImpl getInstance() {
        if (instance == null) {
            instance =  new ResourceFactoryImpl();
        }
        return instance;
    }

    @Override
    public <ResourceType extends Object> ResourceType get(String resourceName) {
        return (ResourceType) resources.get(resourceName);
    }

    @Override
    public void loadAllResources() {
        vfs = new VFSImpl(RESOURCE_ROOT);
        String absoluteResourceRoot = vfs.getAbsolutePath("") + "/";
        Iterator<String> iter = vfs.getIterator("");

        while (iter.hasNext()) {
            String absoluteFilePath = iter.next();
            String relativeFilePath = absoluteFilePath.replace(absoluteResourceRoot, "");

            if (vfs.isFile(absoluteFilePath)) {
                try {
                    resources.put(relativeFilePath, ReadXMLFileSAX.readXML(absoluteFilePath));
                } catch (Exception e) {
                    System.out.println("Resources were not loaded");
                }
            }
        }
        System.out.println("Resources were loaded");

    }

}

