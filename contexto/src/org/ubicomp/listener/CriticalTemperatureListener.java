package org.ubicomp.listener;

import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class CriticalTemperatureListener implements UpdateListener {
	
	 /** Used as the minimum starting threshold for a critical event. */
    private String criticalTemperature = ThresholdersValues.getCriticalTemperatureThreshold();
    private String criticalMultiplier = ThresholdersValues.getCriticalTemperatureMultiplier();
    
    /**
     * If the last event in a critical sequence is this much greater than the first - issue a
     * critical alert.
     */
	public CriticalTemperatureListener() {
        String expression = "select * from TemperatureEvent "
        		  + "match_recognize ( "
                  + "       measures A as temp1, B as temp2, C as temp3, D as temp4 "
                  + "       pattern (A B C D) " 
                  + "       define "
                  + "               A as A.temperature > " + criticalTemperature + ", "
                  + "               B as (A.temperature < B.temperature), "
                  + "               C as (B.temperature < C.temperature), "
                  + "               D as (C.temperature < D.temperature) and "
                  + "               D.temperature > (A.temperature * " + criticalMultiplier + "))";
          
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		StringBuilder sb = new StringBuilder();
		sb.append("---------------------------------");
		sb.append("\n- [CRITICAL] : ");
		sb.append(newEvents[0].getUnderlying().toString());
		sb.append("\n---------------------------------");
		System.out.println(sb.toString());
	}
	
}
