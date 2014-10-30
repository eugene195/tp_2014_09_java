package global.msgsystem;

import global.msgsystem.messages.AbstractMsg;

/**
 * Created by eugene on 10/31/14.
 */
public interface MessageSystem {

    public void register(Runnable abonent, String address);

    public boolean sendMessage(AbstractMsg message, String addressTo);

    public void executeFor(Runnable abonent);


}
