package org.ubicomp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AttentionHumidityListener implements UpdateListener {
	
	private String humidity = ThresholdersValues.getHumidityThreshold();
	private String temperature = ThresholdersValues.getTemperatureThreshold();
	private String luminosity = ThresholdersValues.getLuminosityThreshold();
	
	private static Log log = LogFactory.getLog(AttentionHumidityListener.class);
	

	public AttentionHumidityListener() {
        String expression = "SELECT *" + 
        " FROM TemperatureEvent.std:lastevent() AS temperature," +
        " HumidityEvent.std:lastevent() AS humidity,"+ 
        " LuminosityEvent.std:lastevent() AS luminosity" +
        " WHERE temperature > "+ temperature + 
        " AND humidity < "+ humidity +
        " AND luminosity > "+ luminosity;
        
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ATTENTION] : ");
		sb.append(
				newEvents[0].get("temperature").toString() + ", " + 
				newEvents[0].get("humidity").toString() + ", " +
				newEvents[0].get("luminosity").toString() 
		);
		System.out.println(sb.toString());
		log.warn(sb.toString());
	}
	
}
