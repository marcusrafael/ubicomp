package org.ubicomp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class CriticalListener implements UpdateListener{
	
	  	//CriticalEvent: temperature >= 60, humidity >= 30, luminosity >= 50
	    
	    private static Log log = LogFactory.getLog(CriticalListener.class);
	    
	    /**
	     * If the last event in a critical sequence is this much greater than the first - issue a
	     * critical alert.
	     */
		public CriticalListener() {
	        String expression = "SELECT * " + 
	        		" FROM TemperatureEvent.std:lastevent() AS temperature," +
	                " HumidityEvent.std:lastevent() AS humidity,"+ 
	                " LuminosityEvent.std:lastevent() AS luminosity" +
	        		" WHERE temperature >= 60 AND humidity <= 30 AND luminosity <= 50";

	        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
	        statement.addListener(this);
		}

		public void update(EventBean[] newEvents, EventBean[] oldEvents) {
			StringBuilder sb = new StringBuilder();
			sb.append("[CRITICAL] : ");
			sb.append(
					newEvents[0].get("temperature").toString() + ", " + 
					newEvents[0].get("humidity").toString() + ", " +
					newEvents[0].get("luminosity").toString() 
			);
			System.out.println(sb.toString());
			log.warn(sb.toString());
		}
}
