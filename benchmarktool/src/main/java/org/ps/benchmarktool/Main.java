package org.ps.benchmarktool;

import org.ps.benchmarktool.http.DefaultConnectionFactory;
import org.ps.benchmarktool.http.HttpConnectionFactory;
import org.ps.benchmarktool.http.RequestListener;
import org.ps.benchmarktool.http.RequestTask;
import java.net.URL;

public class Main {
    public static void main(String[] arguments) {
        try {
            HttpConnectionFactory factory = new DefaultConnectionFactory(DefaultConnectionFactory.DEFAULT_TIMEOUT);
            RequestListener listener = new LoggingListener();
            URL url = new URL("http://localhost:4000/opengl/");
            RequestTask task = new RequestTask(factory, listener, url);
            task.run();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
