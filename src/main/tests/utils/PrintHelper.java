package utils;

import java.io.PrintWriter;

/**
 * Created by max on 18.10.14.
 */

public class PrintHelper extends PrintWriter {
    private String json;

    public PrintHelper() {
        super(new EmptyWriter());
    }

    @Override
    public void print(Object obj) {
        this.json = String.valueOf(obj);
    }

    @Override
    public void flush() {}

    public String getJSON() {
        return this.json;
    }
}
