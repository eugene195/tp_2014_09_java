package global.webpages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Евгений on 27.08.2014.
 * Abstract Page controller.
 */
public abstract class WebPage {

    private static final String DEFAULT_TML_PATH = "src/main/tml/";
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
            File file = new File(DEFAULT_TML_PATH + pathToFile);
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
