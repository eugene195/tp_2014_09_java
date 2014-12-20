import global.Abonent;
import global.AddressService;
import global.GameMechanics;
import global.MessageSystem;
import global.mechanics.GameMechanicsImpl;
import global.msgsystem.MessageSystemImpl;
import global.msgsystem.messages.AbstractMsg;
import global.msgsystem.messages.toGameMechanics.AbstractToMechanics;
import global.msgsystem.messages.toGameMechanics.GameSessionsQuery;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.MessageHelper;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by eugene on 12/20/14.
 */
public class MsgSystemTest {
    private final MessageSystemImpl msys = new MessageSystemImpl();
    private final GameMechanicsImpl mechanics = new GameMechanicsImpl(msys);

    class TestThread implements Abonent, Runnable {
        private final String address;
        public ConcurrentLinkedQueue<AbstractMsg> queue = new ConcurrentLinkedQueue<AbstractMsg>();

        public TestThread() {
            address = AddressService.registerServlet();
            msys.register(this);
            msys.register(mechanics);
        }

        public String getAddress() {
            return address;
        }

        public void run() {
            msys.sendMessage(new GameSessionsQuery(address));
            try {
                Thread.sleep(500);
                queue = msys.getQueueState(this);
            } catch (InterruptedException e) {
                System.out.println("Something was gone wrong");
            }
        }
    }

    @Test
    public void test() {
        new Thread(mechanics).start();
        TestThread test = new TestThread();
        new Thread(test).start();
        Assert.assertEquals("Size queue", 1, test.queue.size());
    }

}
