package WebPages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 27.08.2014.
 */
public interface WebPage {

    void handleGet(HttpServletRequest request);

    void loadPage(HttpServletResponse response)
            throws IOException;
}
