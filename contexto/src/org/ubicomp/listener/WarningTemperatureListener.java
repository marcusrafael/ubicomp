package org.ubicomp.listener;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class WarningTemperatureListener implements UpdateListener {
	
	/** If 2 consecutive temperature events are greater than this - issue a warning */
    private static final String WARNING_EVENT_THRESHOLD = "80";
	public WarningTemperatureListener() {
        String expression = "select * from TemperatureEvent "
        		 + "match_recognize ( "
                 + "       measures A as temp1, B as temp2 "
                 + "       pattern (A B) " 
                 + "       define " 
                 + "               A as A.temperature > " + WARNING_EVENT_THRESHOLD + ", "
                 + "               B as B.temperature > " + WARNING_EVENT_THRESHOLD + ")";
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("---------------------------------");
		sb.append("\n- [WARNING] : ");
		sb.append(newEvents[0].getUnderlying().toString());
		sb.append("\n---------------------------------");
		System.out.println(sb.toString());
	}
	
}
