package utils;

import global.msgsystem.MessageSystem;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 18.10.14.
 */

public class MinMessageHelper extends MessageSystem {
    @Override
    public boolean sendMessage(AbstractMsg msg, String unused) {
        return true;
    }
}
