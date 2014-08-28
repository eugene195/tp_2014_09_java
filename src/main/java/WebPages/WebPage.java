package WebPages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 27.08.2014.
 */
public abstract class WebPage {

    public abstract void handleGet(HttpServletRequest request);

    protected void loadPage(HttpServletResponse response)
            throws IOException;
}
