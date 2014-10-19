package global.resources;

import global.resources.sax.ReadXMLFileSAX;
import global.resources.vfs.VFS;
import global.resources.vfs.VFSImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Moiseev Maxim on 18.10.14.
 */
public class ResourceFactory {
    public static final String RESOURCE_ROOT = "data";
    private static ResourceFactory instance;
    private static Map<String, Object> resources = new HashMap<>();
    private static VFS vfs;

    private ResourceFactory() {

    }

    public static ResourceFactory getInstance() {
        if (instance == null) {
            instance =  new ResourceFactory();
            loadResources();
        }
        return instance;
    }

    public Object get(String resourceName){
        return resources.get(resourceName);
    }

    private static void loadResources() {
        VFS vfs = new VFSImpl(RESOURCE_ROOT);
        String absoluteResourceRoot = vfs.getAbsolutePath("") + "/";
        System.out.println("Absolute resource path: " + absoluteResourceRoot);
        Iterator<String> iter = vfs.getIterator("");

        System.out.println("Resources:");
        while (iter.hasNext()) {
            String absoluteFilePath = iter.next();
            String relativeFilePath = absoluteFilePath.replace(absoluteResourceRoot, "");

            if (vfs.isFile(absoluteFilePath)) {
                System.out.println(relativeFilePath);
                resources.put(relativeFilePath, ReadXMLFileSAX.readXML(absoluteFilePath));
            }
        }

    }





}
