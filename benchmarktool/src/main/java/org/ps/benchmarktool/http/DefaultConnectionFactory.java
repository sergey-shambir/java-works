package org.ps.benchmarktool.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.time.Duration;

public class DefaultConnectionFactory implements HttpConnectionFactory {
    private final Duration timeout;

    /**
     * Creates connection factory which uses URL.openConnection
     * @param timeout - connection/read timeout in milliseconds
     */
    public DefaultConnectionFactory(Duration timeout) {
        this.timeout = timeout;
    }

    @Override
    public HttpURLConnection openConnection(URL url) {
        final String protocol = url.getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https")) {
            throw new InvalidParameterException("url must have http/https protocol: " + url.toString());
        }

        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout((int)timeout.toMillis());
            conn.setConnectTimeout((int)timeout.toMillis());
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            return conn;
        } catch (IOException ex) {
            throw new RuntimeException("connection open error: " + ex.getMessage());
        }
    }
}
