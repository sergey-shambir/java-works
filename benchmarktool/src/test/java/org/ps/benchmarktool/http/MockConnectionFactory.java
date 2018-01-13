package org.ps.benchmarktool.http;

import org.powermock.api.mockito.PowerMockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockConnectionFactory implements HttpConnectionFactory {
    private int statusCode;
    private byte[] responseBody;

    public MockConnectionFactory() {
        this.statusCode = HttpURLConnection.HTTP_OK;
        this.responseBody = "Default Response".getBytes();
    }

    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    public void setResponseBody(byte[] value) {
        this.responseBody = value;
    }

    @Override
    public HttpURLConnection openConnection(URL url) {
        ByteArrayInputStream in = new ByteArrayInputStream(this.responseBody);
        HttpURLConnection conn = PowerMockito.mock(HttpURLConnection.class);
        try {
            PowerMockito.when(conn.getResponseCode()).thenReturn(this.statusCode).getMock();
            PowerMockito.when(conn.getInputStream()).thenReturn(in).getMock();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        PowerMockito.doNothing().when(conn).disconnect();
        return conn;
    }
}
