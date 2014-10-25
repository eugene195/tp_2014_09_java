/**
 * Created by max on 25.10.14.
 */
import global.ResourceFactory;
import global.resources.ResourceFactoryImpl;
import global.resources.Resource;
import org.junit.Test;
import org.junit.Assert;

public class ResourcesTest {
    private final ResourceFactory testInstance = ResourceFactoryImpl.getInstance();

    private static final String TEST_XML = "test.xml";

    public static class Admin implements Resource {
        private String fullname;
        private int respect;

        public String getFullName() {
            return this.fullname;
        }

        public int getRespect() {
            return this.respect;
        }
    }

    public ResourcesTest() {
        super();
        this.testInstance.loadAllResources();
    }

    @Test
    public void testAdmin_1() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";
        Admin testRecord = this.testInstance.get(TEST_XML);
        Assert.assertEquals(ErrText, "Kislenko Maksim", testRecord.getFullName());
    }

    @Test
    public void testAdmin_2() throws Exception {
        final String ErrText = "Файл xml прочитан неправильно: ";
        Admin testRecord = this.testInstance.get(TEST_XML);
        Assert.assertEquals(ErrText, 22, testRecord.getRespect());
    }
}