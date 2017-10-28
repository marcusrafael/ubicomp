package org.ubicomp.listener;

import org.ubicomp.UbicompMain;
import org.ubicomp.input.EsperHttpInputAdapter;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class QuitListener implements UpdateListener {
	public QuitListener() {
        String expression = "select * from QuitEvent";
        EPStatement statement = EsperHttpInputAdapter.epService.getEPAdministrator().createEPL(expression);
        statement.addListener(this);
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println("Quit!");
		UbicompMain.running = false;
	}
}
