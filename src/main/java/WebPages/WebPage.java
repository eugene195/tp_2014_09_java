package WebPages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Евгений on 27.08.2014.
 * Abstract Page controller.
 */
public abstract class WebPage {

    public abstract void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException;

//    public abstract void handlePost(HttpServletRequest request, HttpServletResponse response)
//            throws IOException;

    protected void loadPage(String pathToFile) {

    }
}
