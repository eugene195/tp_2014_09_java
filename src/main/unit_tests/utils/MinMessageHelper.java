package utils;

import global.msgsystem.MessageSystemImpl;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 18.10.14.
 */

public class MinMessageHelper extends MessageSystemImpl {
    @Override
    public boolean sendMessage(AbstractMsg msg, String unused) {
        return true;
    }
}
