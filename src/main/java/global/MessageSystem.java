package global;

import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 25.10.14.
 */
public interface MessageSystem {

    void register(Abonent abonent);

    boolean sendMessage(AbstractMsg message);
    void executeFor(Abonent abonent);

}
