package global.msgsystem.messages;

import global.Abonent;

/**
 * Created by max on 26.08.14.
 * Abstract message.
 * It will be used like a callback.
 */
public abstract class AbstractMsg {
    private final String addressFrom;
    private final String addressTo;

    public AbstractMsg(String addressFrom, String addressTo) {
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
    }

    public String getAddressFrom() {
        return this.addressFrom;
    }

    public String getAddressTo() {
        return this.addressTo;
    }

    public abstract void exec(Abonent abonent);
}
