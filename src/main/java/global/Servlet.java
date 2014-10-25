package global;

import global.messagesystem.messages.AbstractMsg;

/**
 * Created by eugene on 10/16/14.
 */
public interface Servlet extends Runnable, javax.servlet.Servlet {

    public void transmitToPage(String URL, AbstractMsg msg);
}
