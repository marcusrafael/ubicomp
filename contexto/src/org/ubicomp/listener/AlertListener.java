package org.ubicomp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AlertListener implements UpdateListener{
	
	//AlertEvent 30 < temperature < 40; humidity <= 50; luminosity <= 50
	private static Log log = LogFactory.getLog(AlertListener.class);
	
	public AlertListener() {
        String expression = "SELECT * " + 
        		" FROM TemperatureEvent.std:lastevent() AS temperature," +
                " HumidityEvent.std:lastevent() AS humidity,"+ 
                " LuminosityEvent.std:lastevent() AS luminosity" +
        		" WHERE (temperature >= 30 AND temperature < 40)" + 
        		" AND humidity <= 50 AND luminosity <= 50";

        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ALERT] : ");
		sb.append(
				newEvents[0].get("temperature").toString() + ", " + 
				newEvents[0].get("humidity").toString() + ", " +
				newEvents[0].get("luminosity").toString() 
		);
		System.out.println(sb.toString());
		log.warn(sb.toString());
	}
}
