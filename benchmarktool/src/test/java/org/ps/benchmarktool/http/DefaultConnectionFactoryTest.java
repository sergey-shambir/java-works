package org.ps.benchmarktool.http;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.time.Duration;

public class DefaultConnectionFactoryTest extends Assert {
    @Test(expected = InvalidParameterException.class)
    public void urlWithNoHttpProtocolThrowsException() {
        try {
            DefaultConnectionFactory factory = new DefaultConnectionFactory(Duration.ZERO);
            factory.openConnection(new URL("ftp://myresource.com"));
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void correctUrlOpenedSuccessful() {
        try {
            DefaultConnectionFactory factory = new DefaultConnectionFactory(Duration.ofMillis(10));
            HttpURLConnection conn = factory.openConnection(new URL("http://test.com"));
            assertNotNull(conn);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }
}