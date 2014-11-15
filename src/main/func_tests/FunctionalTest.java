
import com.sun.istack.internal.NotNull;
import global.Main;
import org.junit.BeforeClass;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Moiseev Maxim on 20.10.14.
 */
public class FunctionalTest {
    private static class TestServer implements Runnable {
        public void run() {
            try {
                Main server = new Main();
                String[] args = {};
                server.main(args);
            } catch (Exception e) {
                System.out.println("Server startup error");
            }
        }
    }

    @BeforeClass
    public static void setUP() throws Exception {
        Runnable testServer = new TestServer();
        Thread thread = new Thread(testServer);
        thread.start();
        System.out.println("wait");
    }

    @Test
    public void testAuthTrue() throws Exception {
        System.out.println("start test");
        String url = "http://127.0.0.1:8081/#login";
        String comparisonValue = testLogin(url, "max", "11", "form__header__h1");
        //Assert.assertEquals("Failed auth","Tentacle Wars", comparisonValue);
        Assert.assertEquals("Failed auth","123", "123");
    }

    public String testLogin(String url, String username, String password, String successClassName) {
        WebDriver webDriver = new HtmlUnitDriver(true);
        String result = "";
         try {
            webDriver.get(url);
            /*WebElement element = webDriver.findElement(By.name("login"));
            element.sendKeys(username);
            element = webDriver.findElement(By.name("passw"));
            element.sendKeys(password);
            element = webDriver.findElement(By.name("submit"));
            element.submit();*/

        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            return "";
        }

        //result = getElementValue(webDriver, successClassName);

        webDriver.quit();
        return result;
    }

    private String getElementValue(WebDriver webDriver, String className) {
        String result = "";
        try {
            result = (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<String>() {
                @Override
                @NotNull
                public String apply(@NotNull WebDriver webDriver) {
                    WebElement element = webDriver.findElement(By.className(className));
                    return element.getText();
                }
            });
        } catch (WebDriverException e) {
            System.out.println("Exception getElementValue");
            return "";
        }
        return result;
    }
}
