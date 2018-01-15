package org.ps.benchmarktool.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestRunnerTest extends Assert {
    private MockConnectionFactory factory;
    private MockRequestListener listener;

    @Before
    public void setup() {
        try {
            this.factory = new MockConnectionFactory();
            this.listener = new MockRequestListener();
        } catch (Exception ex) {
            fail("cannot mock before running tests");
        }
    }

    @Test
    public void testSendingRequests() {
        try {
            HttpRequestRunner runner = new HttpRequestRunner(this.factory, this.listener, 4);
            runner.requestUrlMultipleTimes(new URL("http://example.com"), 1007);

            assertEquals(1007, this.listener.getRequestCount());
            assertEquals(4, this.listener.getConcurrencyLevel());
            assertEquals(1007, this.listener.getSucceedRequestCount());
        } catch (MalformedURLException ex) {
            fail("internal error: " + ex.getMessage());
        }
    }
}
