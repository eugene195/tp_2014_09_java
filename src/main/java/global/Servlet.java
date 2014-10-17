package global;

import global.messages.AbstractMsg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by eugene on 10/16/14.
 */
public interface Servlet extends Runnable, javax.servlet.Servlet {

    public void transmitToPage(String URL, AbstractMsg msg);
}
