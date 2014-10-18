package utils;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by max on 18.10.14.
 */

public class PrintHelper extends PrintWriter {
    private final ArrayList<String> outs = new ArrayList<>();
    private int I;

    public PrintHelper() {
        super(new EmptyWriter());
        this.I = 0;
    }

    @Override
    public void print(Object obj) {
        this.outs.add(String.valueOf(obj));
    }

    @Override
    public void print(String obj) {
        this.outs.add(obj);
    }

    @Override
    public void flush() {
        this.I = 0;
        this.outs.clear();
    }

    public String getPrintOut() {
        return this.outs.get(I++);
    }
}
