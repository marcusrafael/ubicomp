package org.ubicomp;

import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.ubicomp.input.EsperHttpInputAdapter;
import org.ubicomp.tests.SupportHTTPClient;
import org.ubicomp.listener.*;

public class UbicompMain {

	public static boolean running = true;
	
	public static void main(String[] args) throws Exception {		
    	BasicConfigurator.configure();
    	
        PropertyConfigurator.configure("log4j.properties");

        EsperHttpInputAdapter test = new EsperHttpInputAdapter();
        test.run();

        /* Testing */
        System.out.println("Main> Starting Listeners...");
        new LiveListener();
        new QuitListener();

        // attention events        
        new AttentionTemperatureListener();
        new AttentionHumidityListener();
        new AttentionListener();
        
        // warning events
        new AlertTemperatureListener();
        new AlertListener();
        
        // critical events
        new CriticalTemperatureListener();
        new CriticalListener();
        
        System.out.println("Main> Listeners Done!");

        SupportHTTPClient client = new SupportHTTPClient();
        client.request(Integer.parseInt(EsperHttpInputAdapter.HTTP_PORT), "sendevent", "stream", "LiveEvent");
        while (running) {
        		try {
        			Thread.sleep(1000);
        		} catch (InterruptedException e) {
        			System.out.println("Failed to process event.");
        			e.printStackTrace();
        		}
        }

        test.destroy();
    }
}
