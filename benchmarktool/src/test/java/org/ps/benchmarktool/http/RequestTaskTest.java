package org.ps.benchmarktool.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestTaskTest extends Assert {
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
    public void testOkRequest() throws MalformedURLException {
        final int expectedStatus = HttpURLConnection.HTTP_OK;
        byte[] expectedBody = "method testSuccessfulRequest".getBytes();
        this.factory.setStatusCode(expectedStatus);
        this.factory.setResponseBody(expectedBody);

        RequestTask task = new RequestTask(this.factory, this.listener, new URL("http://google.com"));
        task.run();
        assertTrue(this.listener.getLastTimeSpent().toMillis() >= 0);
        assertEquals(this.listener.getLastByteCount(), expectedBody.length);
        assertEquals(this.listener.getLastStatusCode(), expectedStatus);
    }

    @Test
    public void testBadRequest() throws MalformedURLException {
        final int expectedStatus = HttpURLConnection.HTTP_BAD_REQUEST;
        byte[] expectedBody = {};
        this.factory.setStatusCode(expectedStatus);
        this.factory.setResponseBody(expectedBody);

        RequestTask task = new RequestTask(this.factory, this.listener, new URL("http://yandex.ru"));
        task.run();
        assertTrue(this.listener.getLastTimeSpent().toMillis() >= 0);
        assertEquals(this.listener.getLastByteCount(), expectedBody.length);
        assertEquals(this.listener.getLastStatusCode(), expectedStatus);
    }
}
