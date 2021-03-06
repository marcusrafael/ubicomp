package org.ubicomp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ubicomp.input.EsperHttpInputAdapter;
import org.ubicomp.tests.SupportHTTPClient;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AttentionTemperatureListener implements UpdateListener {
	
	private String temperature = ThresholdersValues.getTemperatureThreshold();
	
	private static Log log = LogFactory.getLog(AttentionTemperatureListener.class);

	public AttentionTemperatureListener() {
        String expression = "select * from TemperatureEvent "
				+ "match_recognize ( "
				+ "       measures A as temp1, B as temp2 "
				+ "       pattern (A B) " 
				+ "       define " 
				+ "               A as A.temperature > " + temperature + ", "
				+ "               B as B.temperature > " + temperature + ")";
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ATTENTION] : ");
		sb.append(newEvents[0].getUnderlying().toString());
		System.out.println(sb.toString());
		log.warn(sb.toString());
	}
	
}
