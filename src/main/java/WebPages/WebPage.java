package WebPages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Евгений on 27.08.2014.
 * Abstract Page controller.
 */
public abstract class WebPage {

    private final static String DEFAULT_TML_PATH = "../../tml/";

    public abstract void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException;

//    public abstract void handlePost(HttpServletRequest request, HttpServletResponse response)
//            throws IOException;

    protected String loadPage(String pathToFile)
            throws IOException
    {
        String page = null;
        try {
            File file = new File(DEFAULT_TML_PATH + pathToFile);


            System.out.println(DEFAULT_TML_PATH + pathToFile);


            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)
                    )
            );

            while ((page = br.readLine()) != null) {
                /* System.out.println(page); */
            }
            br.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return page;
    }
}
