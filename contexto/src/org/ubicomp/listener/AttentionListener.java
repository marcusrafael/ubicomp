package org.ubicomp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AttentionListener implements UpdateListener{

	//AttentionEvent: 40 <= temperature < 60, humidity <= 50, luminosity <= 50
    private static Log log = LogFactory.getLog(AttentionListener.class);
	
    public AttentionListener() {
        String expression = "SELECT * " + 
        		" FROM TemperatureEvent.std:lastevent() AS temperature," +
                " HumidityEvent.std:lastevent() AS humidity,"+ 
                " LuminosityEvent.std:lastevent() AS luminosity" +
                " WHERE (temperature >= 40 AND temperature < 60)" + 
        		" AND humidity <= 50 AND luminosity <= 50";

        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
    }

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ATTENTION] : ");
		sb.append(newEvents[0].getUnderlying().toString());
		System.out.println(sb.toString());
		log.warn(sb.toString());		
	}
}
