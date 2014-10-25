package global;

import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 25.10.14.
 */
public interface MessageSystem {

    void register(Runnable abonent, String address);

    boolean sendMessage(AbstractMsg message, String addressTo);

    void executeFor(Runnable abonent);

    void printAddresses();
}
