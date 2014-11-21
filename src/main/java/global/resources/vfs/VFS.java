package global.resources.vfs;

import java.util.Iterator;

public interface VFS {
    boolean isFile(String path);

    String getAbsolutePath(String file);

    Iterator<String> getIterator(String startDir);
}
