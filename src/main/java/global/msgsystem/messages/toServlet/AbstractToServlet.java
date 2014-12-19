package global.msgsystem.messages.toServlet;

import global.Abonent;
import global.AddressService;
import global.Servlet;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 22.11.14.
 */
public abstract class AbstractToServlet extends AbstractMsg {

    public AbstractToServlet(String addressFrom) {
        super(addressFrom, AddressService.getServletAddr());
    }

    public abstract void exec(Servlet servlet);

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof Servlet) {
            Servlet servlet = (Servlet) abonent;
            exec(servlet);
        }
        else {
            System.out.println("Exception during AbstractToServlet.exec");
        }
    }
}
