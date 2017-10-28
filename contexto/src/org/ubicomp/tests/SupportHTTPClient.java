package org.ubicomp.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SupportHTTPClient {
    private static Log log = LogFactory.getLog(SupportHTTPClient.class);

    private HttpClient httpclient;

    public SupportHTTPClient() {
        httpclient = new DefaultHttpClient();
    }

    public void request(int port, String document, String... params) throws Exception {
        String uri = "http://localhost:" + port + "/" + document;
        URI requestURI = withQuery(new URI(uri), params);
        log.info("Requesting from URI " + requestURI);
        HttpGet httpget = new HttpGet(requestURI);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = null;
        try {
            responseBody = httpclient.execute(httpget, responseHandler);
        } catch (IOException e) {
            throw new RuntimeException("Error executing request:" + e.getMessage());
        }
    }

    public static URI withQuery(URI uri, String... params) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < params.length; i += 2) {
            String key = params[i];
            String val = i + 1 < params.length ? params[i + 1] : "";
            map.put(key, val);
        }
        return withQuery(uri, map);
    }

    public static URI withQuery(URI uri, Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        char separator = '?';
        for (Map.Entry<String, String> param : params.entrySet()) {
            query.append(separator);
            separator = '&';
            try {
                query.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                if (param.getValue().length() != 0) {
                    query.append('=');
                    query.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return URI.create(uri.toString() + query.toString());
    }

    public static void main(String args[]) throws Exception {
    	BasicConfigurator.configure();
    	SupportHTTPClient client = new SupportHTTPClient();
        
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "500", "timeOfReading",new Date().toString());
    	client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "50");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "60");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "60");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "50");
    	
    	
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "50");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "81");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "81");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "50");
        
    	client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "101");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "102");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "103");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "155");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "50");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "500");
        client.request(8085, "sendevent", "stream", "TemperatureEvent", "temperature", "500");

    }


}
