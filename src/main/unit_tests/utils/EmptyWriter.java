package utils;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by max on 18.10.14.
 */
class EmptyWriter extends Writer {
    @Override
    public void write(char cbuf[], int off, int len) throws IOException {}

    @Override
    public void flush() {}

    @Override
    public void close() {}
}