package com.ubicomp.context.cep.input;

import java.util.Properties;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.http.EsperIOHTTPAdapter;
import com.espertech.esperio.http.EsperIOHTTPAdapterPlugin;
import com.espertech.esperio.http.config.ConfigurationHTTPAdapter;
import com.espertech.esperio.http.config.Request;

public class EsperioHttpInputAdapter {
    public static EPServiceProvider epService;
    public static String HTTP_PORT = "8083";   
    
    public void run() throws Exception {
        boolean isNio = true;

        String esperIOHTTPConfig = "<esperio-http-configuration>\n" +
                "\t<service name=\"service1\" port=\"" + HTTP_PORT + "\" nio=\"" + isNio + "\"/>\n" +
                "\t<get service=\"service1\" pattern=\"*\"/>\n" +
                "</esperio-http-configuration>";

        Configuration config = new Configuration();
        config.addPluginLoader("EsperIOHTTPAdapter", EsperIOHTTPAdapterPlugin.class.getName(), new Properties(), esperIOHTTPConfig);

        config.addEventTypeAutoName("org.cor.cep.event");
    }

    public void destroy() {
        epService.destroy();
    }
}