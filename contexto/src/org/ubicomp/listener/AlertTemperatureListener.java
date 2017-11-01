package org.ubicomp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AlertTemperatureListener implements UpdateListener {
	
    private String warningTemperature = ThresholdersValues.getWarningTemperatureThreshold();
    
    private static Log log = LogFactory.getLog(AlertTemperatureListener.class);
    
    /** If 2 consecutive temperature events are greater than this - issue a warning */    
	public AlertTemperatureListener() {
        String expression = "select * from TemperatureEvent "
        		 + "match_recognize ( "
                 + "       measures A as temp1, B as temp2 "
                 + "       pattern (A B) " 
                 + "       define " 
                 + "               A as A.temperature > " + warningTemperature + ", "
                 + "               B as B.temperature > " + warningTemperature + ")";
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("[WARNING] : ");
		sb.append(newEvents[0].getUnderlying().toString());
		System.out.println(sb.toString());
		log.warn(sb.toString());
	}
	
}
