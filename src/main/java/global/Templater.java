package global;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Eugene on 8/30/2014.
 * This class implements Singleton
 */
public class Templater {

    private static Templater instance;
    private static final String DEFAULT_TML_PATH = "src/main/tml/";
    private final VelocityEngine vEngine;

    public static Templater getInstance(){
        if (instance == null) {
            instance = new Templater();
        }
        return  instance;
    }

    private Templater() {
        Properties props = new Properties();
        props.put("file.resource.loader.path",DEFAULT_TML_PATH);

        this.vEngine = new VelocityEngine();
        this.vEngine.init(props);
    }

    public String generate(String filePathToVML, Map<String, Object> pageContext) {
        Template template = this.vEngine.getTemplate(filePathToVML);
        VelocityContext context = new VelocityContext(pageContext);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
