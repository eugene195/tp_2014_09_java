package global;

import global.msgsystem.messages.toServlet.AbstractToServlet;

/**
 * Created by eugene on 10/16/14.
 */
public interface Servlet extends Runnable, javax.servlet.Servlet, Abonent {
    public void transmitToPage(String URL, AbstractToServlet msg);
}
