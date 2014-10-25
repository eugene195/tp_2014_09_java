package global.msgsystem;

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
public class MessageSystem {

    private final Map<Runnable, ConcurrentLinkedQueue<AbstractMsg>> messages = new HashMap<>();
    private final Map<String, Runnable> addresses = new HashMap<>();

    //TODO: append params validation
    public void register(Runnable abonent, String address) {
        this.addresses.put(address, abonent);
        this.messages.put(abonent, new ConcurrentLinkedQueue<>());
    }

    //TODO: append unregister method

    public boolean sendMessage(AbstractMsg message, String addressTo) {
        boolean result;
        Runnable reciever = this.addresses.get(addressTo);
        Queue<AbstractMsg> messageQueue = this.messages.get(reciever);

        try {
            messageQueue.add(message);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public void executeFor(Runnable abonent) {
        Queue<AbstractMsg> messageQueue = this.messages.get(abonent);

        while (!messageQueue.isEmpty()) {
            AbstractMsg message = messageQueue.poll();
            message.exec(abonent);
        }
    }

    /**
     * The helpful tool for a programmer.
     */
    public void printAddresses() {
        System.out.println("Current content of address set: ");
        this.addresses.forEach((address, obj) -> System.out.println(address));
    }
}
