package utils;

import global.implementations.MessageSystemImpl;
import global.messages.AbstractMsg;

/**
 * Created by max on 18.10.14.
 */

public class MinMessageHelperImpl extends MessageSystemImpl {
    @Override
    public boolean sendMessage(AbstractMsg msg, String unused) {
        return true;
    }
}
