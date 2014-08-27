package WebPages;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 27.08.2014.
 */
public interface WebPage {
    public void loadPage(HttpServletResponse response)
            throws IOException;


}
