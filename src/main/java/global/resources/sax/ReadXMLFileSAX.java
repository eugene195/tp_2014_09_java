package global.resources.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ReadXMLFileSAX {
    public static Object readXML(String xmlFile) throws Exception{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        SaxHandler handler = new SaxHandler();
        saxParser.parse(xmlFile, handler);

        return handler.getObject();
    }

}
