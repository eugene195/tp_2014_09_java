package utils;

<<<<<<< HEAD
import global.msgsystem.MessageSystem;
=======
>>>>>>> master
import global.msgsystem.MessageSystemImpl;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 18.10.14.
 */
public class MessageHelper extends MessageSystemImpl {
    private AbstractMsg msg;

    @Override
    public boolean sendMessage(AbstractMsg msg, String unused) {
        this.msg = msg;
        return true;
    }

    public <MsgType extends AbstractMsg> MsgType getMsg () {
        return (MsgType) this.msg;
    }
}
