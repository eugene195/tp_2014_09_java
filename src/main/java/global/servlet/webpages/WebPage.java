package global.servlet.webpages;

import global.msgsystem.messages.AbstractMsg;
import global.servlet.Templater;
import org.apache.velocity.exception.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by Евгений on 27.08.2014.
 * Abstract Page controller.
 */
public abstract class WebPage {
    protected static final String CONTENT_TYPE = "text/html;charset=utf-8";
    protected boolean zombie;
    protected static final String OK = "1";
    protected static final String FAILED = "-1";

    public WebPage() {
        this.zombie = false;
    }

    public void handleGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        System.out.println("Warning! WebPage.handleGet was invoked");
    }

    /**
     * HandlePost can be overriden by concrete pages or not.
     * @param request
     * @param response
     * @throws IOException
     */
    public void handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        System.out.println("Warning! WebPage.handlePost was invoked");
    }

    protected String generateHTML(String pathToVML, Map<String, Object> context) {
        String page = "";
        try {
            page = Templater.getInstance().generate(pathToVML, context);
        }
        catch (ResourceNotFoundException exception) {
            page = "Template Page not found" + pathToVML;
        }
        return page;
    }

    /**
     * It is a final handler of the async query, from the Concrete Page.
     * @param msg message to page
     */
    public void finalizeAsync(AbstractMsg msg) {
        System.out.println("Warning! WebPage.finalizeAsync was invoked");
    }

    /**
     * This method enforces the ServletImpl thread await for the async query.
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

    public boolean isZombie() {
        return this.zombie;
    }

    public void resume() {
        this.zombie = false;
    }
}
