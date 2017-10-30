package org.ubicomp;

import org.apache.log4j.BasicConfigurator;
import org.ubicomp.input.EsperHttpInputAdapter;
import org.ubicomp.tests.SupportHTTPClient;
import org.ubicomp.listener.*;

public class UbicompMain {

	public static boolean running = true;
	
	public static void main(String[] args) throws Exception {
    	BasicConfigurator.configure();

        EsperHttpInputAdapter test = new EsperHttpInputAdapter();
        test.run();

        /* Testing */
        System.out.println("Main> Starting Listeners...");
        new LiveListener();
        new QuitListener();
        // attention events        
        new AttentionTemperatureListener();
        new AttentionHumidityListener();
        
        // warning events
        new WarningTemperatureListener();
        
        // critical events
        new CriticalTemperatureListener();
        
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
