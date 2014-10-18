import global.MessageSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AdminPageTest {


    private HttpServletRequest request;
    private MessageSystem msys;

    @Before
    public void setUp() throws Exception {
        this.msys = new MessageSystem();
        this.request = mock(HttpServletRequest.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testHandleGet() throws Exception {

    }
}