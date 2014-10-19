package global;

import global.messages.AbstractMsg;

/**
 * Created by eugene on 10/20/14.
 */
public interface MessageSystem {
    public void register(Runnable abonent, String address);

    public boolean sendMessage(AbstractMsg message, String addressTo);

    public void executeFor(Runnable abonent);

    public void printAddresses();
}
