package org.ps.benchmarktool.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

public class DefaultConnectionFactory implements HttpConnectionFactory {
    public static final int DEFAULT_TIMEOUT = 30000;

    private final int timeout;

    /**
     * Creates connection factory which uses URL.openConnection
     * @param timeout - connection/read timeout in milliseconds
     */
    public DefaultConnectionFactory(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public HttpURLConnection openConnection(URL url) throws IOException {
        if (url.getProtocol() != "http" && url.getProtocol() != "https") {
            throw new InvalidParameterException("url must have http/https protocol: " + url.toString());
        }

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setReadTimeout(timeout);
        conn.setConnectTimeout(timeout);
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        return conn;
    }
}
