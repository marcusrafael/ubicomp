package com.ubicomp.context.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubicomp.context.cep.event.TemperatureEvent;

@Component
public class AttentionEventSubscriber implements StatementSubscriber {

	/** Logger */
	private static Logger LOG = LoggerFactory.getLogger(AttentionEventSubscriber.class);


	private static final String ATTENTION_EVENT_THRESHOLD = "50";


	/**
	 * {@inheritDoc}
	 */
	public String getStatement() {

		String attentionEventExpression = "select * from TemperatureEvent "
				+ "match_recognize ( "
				+ "       measures A as temp1, B as temp2 "
				+ "       pattern (A B) " 
				+ "       define " 
				+ "               A as A.temperature > " + ATTENTION_EVENT_THRESHOLD + ", "
				+ "               B as B.temperature > " + ATTENTION_EVENT_THRESHOLD + ")";

		return attentionEventExpression ;
	}

	/**
	 * Listener method called when Esper has detected a pattern match.
	 */
	public void update(Map<String, TemperatureEvent> eventMap) {

		TemperatureEvent temp1 = (TemperatureEvent) eventMap.get("temp1");
		TemperatureEvent temp2 = (TemperatureEvent) eventMap.get("temp2");

		StringBuilder sb = new StringBuilder();
		sb.append("---------------------------------");
		sb.append("\n- [ATTENTION] : " + temp1 + "," + temp2 + "-");
		sb.append("\n---------------------------------");

		LOG.debug(sb.toString());
	}
}
