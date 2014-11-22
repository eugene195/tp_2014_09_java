package global.resources.vfs;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class VFSImpl implements VFS {

    private String root;

    public VFSImpl(String root) {
        this.root = root;
    }

    @Override
    public boolean isFile(String path) {
        return new File(path).isFile();
    }

    @Override
    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }

    private class FileIterator implements Iterator<String> {

        private Queue<File> files = new LinkedList<>();

        public FileIterator(String path) {
            files.add(new File(root + path));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file = files.peek();
            if (file.isDirectory()) {
                for (File subFile : file.listFiles()) {
                    files.add(subFile);
                }
            }

            return files.poll().getAbsolutePath();
        }

        @Override
        public void remove() {
        }

    }

    @Override
    public String getAbsolutePath(String file) {
        return new File(root + file).getAbsolutePath();
    }

}
