package global.msgsystem;

import global.Abonent;
import global.MessageSystem;
import global.msgsystem.messages.AbstractMsg;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by max on 26.08.14.
 * The Class for an asyncronous interaction between the system threads.
 * It uses String instances for addressing.
 * There are no managing of the address uniqueness and it has to be done by a programmer.
 */
public class MessageSystemImpl implements MessageSystem {

    private final Map<String, ConcurrentLinkedQueue<AbstractMsg>> messages = new HashMap<>();


    @Override
    public void register(Abonent abonent) {
        String address = abonent.getAddress();
        this.messages.put(address, new ConcurrentLinkedQueue<>());
    }

    //TODO: append unregister method

    @Override
    public boolean sendMessage(AbstractMsg message) {
        boolean result;
        String addressTo = message.getAddressTo();
        Queue<AbstractMsg> messageQueue = this.messages.get(addressTo);

        try {
            messageQueue.add(message);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public void executeFor(Abonent abonent) {
        String address = abonent.getAddress();
        Queue<AbstractMsg> messageQueue = this.messages.get(address);

        while (!messageQueue.isEmpty()) {
            AbstractMsg message = messageQueue.poll();
            message.exec(abonent);
        }
    }

    public ConcurrentLinkedQueue<AbstractMsg> getQueueState(Abonent abonent) {
        String address = abonent.getAddress();
        return this.messages.get(address);
    }
}
