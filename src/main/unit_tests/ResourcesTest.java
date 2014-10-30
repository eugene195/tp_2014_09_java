/**
 * Created by max on 25.10.14.
 */
import global.ResourceFactory;
import global.resources.Admin;
import global.resources.ResourceFactoryImpl;
import global.resources.Resource;
import org.junit.Test;
import org.junit.Assert;

public class ResourcesTest {
    private final ResourceFactory testInstance = ResourceFactoryImpl.getInstance();

    private static final String TEST_XML = "test.xml";
    private static final String errText = "Файл xml прочитан неправильно: ";

    public ResourcesTest() {
        super();
        this.testInstance.loadAllResources(ResourceFactory.RESOURCE_ROOT + "/tests");
    }

    @Test
    public void testAdmin_1() throws Exception {
        Admin testRecord = this.testInstance.get(TEST_XML);
        Assert.assertEquals(errText, "Kislenko Maksim", testRecord.getFullName());
        Assert.assertEquals(errText, 22, testRecord.getRespect());
    }
}