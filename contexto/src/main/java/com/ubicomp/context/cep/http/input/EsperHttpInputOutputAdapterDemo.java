package com.ubicomp.context.cep.http.input;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esperio.http.EsperIOHTTPAdapter;
import com.espertech.esperio.http.EsperIOHTTPAdapterPlugin;
import com.espertech.esperio.http.config.ConfigurationHTTPAdapter;
import com.espertech.esperio.http.config.Request;
import com.ubicomp.context.cep.http.SupportHTTPClient;
import com.ubicomp.context.cep.http.SupportHTTPServer;

import java.sql.Time;
import java.util.Properties;

public class EsperHttpInputOutputAdapterDemo {
    private final static String ENGINE_URI = "CEP-SERVICE-0000";
    public void run() throws Exception {
        String port = "8083";
        boolean isNio = true;

        // Configure HTTP Output Adapter
        ConfigurationHTTPAdapter adapterConfig = new ConfigurationHTTPAdapter();
        Request requestOne = new Request();
        requestOne.setStream("SupportBean");
        requestOne.setUri("http://localhost:8084/root");
        adapterConfig.getRequests().add(requestOne);
        Request requestTwo = new Request();
        requestTwo.setStream("SupportBean");
        requestTwo.setUri("http://localhost:8085/root/${stream}/${ipAddress}/${page}");
        adapterConfig.getRequests().add(requestTwo);
        EsperIOHTTPAdapter httpOutputAdapter = new EsperIOHTTPAdapter(adapterConfig, ENGINE_URI);

        // Configure HTTP Input Adapter
        String esperIOHTTPConfig = "<esperio-http-configuration>\n" +
                "<service name=\"service1\" port=\"" + port + "\" nio=\"" + isNio + "\"/>" +
                "<get service=\"service1\" pattern=\"*\"/>" +
                "</esperio-http-configuration>";
        Configuration config = new Configuration();
        config.addPluginLoader("EsperIOHTTPAdapter", EsperIOHTTPAdapterPlugin.class.getName(), new Properties(), esperIOHTTPConfig);
        config.addEventTypeAutoName("com.ubicomp.context.cep.event");
        /**
         * Create CEP Engine Instance
         */
        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, config);
 
        /**
         * Publish EPL Statement
         */
        String expression = "insert into SupportBean select ipAddress, page, date from AccessLogEvent.win:time(30 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        /**
         * Start up HTTP Output Adapter
         */
        httpOutputAdapter.start();
        SupportHTTPServer server8084 = new SupportHTTPServer(8084);
        server8084.start();
        SupportHTTPServer server8085 = new SupportHTTPServer(8085);
        server8085.start();
        
        MyListener listener = new MyListener();
        statement.addListener(listener);
        
        Thread.sleep(10000);
        SupportHTTPClient client = new SupportHTTPClient(8083);
        client.request(8083, "sendevent", "stream", "AccessLogEvent", "ipAddress", "localhost", "page", "mypage", "date", "mydate");
        
        client.request(8083, "sendevent", "stream", "AccessLogEvent", "ipAddress", "localhost2", "page", "mypage2", "date", "mydate2");
        /**
         * Stop down HTTP Output Adapter
         */
//        server8084.stop();
//        server8085.stop();
        /**
         * Destroy CEP Engine Instance
         */
//        epService.destroy();
    }
    
    public class MyListener implements UpdateListener {
        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            EventBean event = newEvents[0];
            System.out.println("ipAddress=" + event.get("ipAddress"));
            System.out.println("page=" + event.get("page"));
            System.out.println("date=" + event.get("date"));
        }
    }
    public static void main(String[] args) throws Exception {
        EsperHttpInputOutputAdapterDemo startMainClass = new EsperHttpInputOutputAdapterDemo();
    	startMainClass.run();
    }
}