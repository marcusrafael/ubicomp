package org.ubicomp.listener;

import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AttentionTemperatureListener implements UpdateListener {
	
	private static final String ATTENTION_EVENT_THRESHOLD = "50";
	public AttentionTemperatureListener() {
        String expression = "select * from TemperatureEvent "
				+ "match_recognize ( "
				+ "       measures A as temp1, B as temp2 "
				+ "       pattern (A B) " 
				+ "       define " 
				+ "               A as A.temperature > " + ATTENTION_EVENT_THRESHOLD + ", "
				+ "               B as B.temperature > " + ATTENTION_EVENT_THRESHOLD + ")";
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("---------------------------------");
		sb.append("\n- [ATTENTION] : ");
		sb.append(newEvents[0].getUnderlying().toString());
		sb.append("\n---------------------------------");
		System.out.println(sb.toString());
	}
	
}
