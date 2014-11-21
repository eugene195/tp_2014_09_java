package global.msgsystem.messages.toServlet;

import global.Servlet;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 22.11.14.
 */
public abstract class AbstractToServlet extends AbstractMsg {

    public abstract void exec(Servlet servlet);

    @Override
    public void exec(Runnable abonent) {
        if (abonent instanceof Servlet) {
            Servlet servlet = (Servlet) abonent;
            exec(servlet);
        }
        else {
            System.out.println("Exception during AbstractToServlet.exec");
        }
    }
}
