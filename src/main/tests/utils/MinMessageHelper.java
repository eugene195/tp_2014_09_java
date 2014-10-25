package utils;

import global.messagesystem.MessageSystem;
import global.messagesystem.messages.AbstractMsg;

/**
 * Created by max on 18.10.14.
 */

public class MinMessageHelper extends MessageSystem {
    @Override
    public boolean sendMessage(AbstractMsg msg, String unused) {
        return true;
    }
}
