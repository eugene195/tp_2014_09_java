package global.msgsystem.messages;

import global.Abonent;

/**
 * Created by max on 26.08.14.
 * Abstract message.
 * It will be used like a callback.
 */
public abstract class AbstractMsg {
    public abstract void exec(Abonent abonent);
}
