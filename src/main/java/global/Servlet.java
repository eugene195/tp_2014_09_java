package global;

import global.msgsystem.messages.AbstractMsg;

/**
 * Created by eugene on 10/16/14.
 */
public interface Servlet extends Runnable, javax.servlet.Servlet {
    void transmitToPage(String URL, AbstractMsg msg);
}
