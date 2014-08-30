package global.webpages;

import global.messages.AbstractMsg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Евгений on 27.08.2014.
 * Abstract Page controller.
 */
public abstract class WebPage {
    // Important variables for every page
    public static final String URL = "";
    public static final String TML_PATH = "";


    private static final String TML_CATALOG = "src/main/tml/";
    protected static final String CONTENT_TYPE = "text/html;charset=utf-8";
    protected boolean zombie;

    public WebPage() {
        this.zombie = false;
    }

    public abstract void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException;

    /**
     * HandlePost can be overriden by concrete pages or not.
     * @param request
     * @param response
     * @throws IOException
     */
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    protected String loadPage(String pathToFile)
            throws IOException
    {
        String page = "";
        try {
            File file = new File(TML_CATALOG + pathToFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line = br.readLine();
            while (line != null) {
                page += line;
                line = br.readLine();
            }
            br.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return page;
    }

    /**
     * It is a final handler of the async query, from the Concrete Page.
     * @param msg message to page
     */
    public void finalize(AbstractMsg msg) {
        System.out.println("Warning! WebPage.finalize was invoked");
    }

    /**
     * This method enforces the Servlet thread await for the async query.
     */
    public void setZombie() {
        this.zombie = true;

        while (this.zombie) {
            try {
                Thread.sleep(10);
            }
            catch (Exception e) {
                System.out.println("Zombie was interrupted");
            }
        }
    }

    public void resume() {
        this.zombie = false;
    }
}
