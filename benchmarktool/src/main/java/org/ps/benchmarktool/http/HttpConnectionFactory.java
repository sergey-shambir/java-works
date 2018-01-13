package org.ps.benchmarktool.http;

import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpConnectionFactory { HttpURLConnection openConnection(URL url); }
