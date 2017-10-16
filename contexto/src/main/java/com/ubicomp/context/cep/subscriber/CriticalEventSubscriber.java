package com.ubicomp.context.cep.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubicomp.context.cep.event.HumidityEvent;

@Component
public class CriticalEventSubscriber implements StatementSubscriber {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(CriticalEventSubscriber.class);

    /** Used as the minimum starting threshold for a critical event. */
    private static final String CRITICAL_EVENT_THRESHOLD = "100";
    
    /**
     * If the last event in a critical sequence is this much greater than the first - issue a
     * critical alert.
     */
    private static final String CRITICAL_EVENT_MULTIPLIER = "1.5";
    
    /**
     * {@inheritDoc}
     */
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String crtiticalEventExpression = "select * from TemperatureEvent "
                + "match_recognize ( "
                + "       measures A as temp1, B as temp2, C as temp3, D as temp4 "
                + "       pattern (A B C D) " 
                + "       define "
                + "               A as A.temperature > " + CRITICAL_EVENT_THRESHOLD + ", "
                + "               B as (A.temperature < B.temperature), "
                + "               C as (B.temperature < C.temperature), "
                + "               D as (C.temperature < D.temperature) and D.temperature > (A.temperature * " + CRITICAL_EVENT_MULTIPLIER + ")" + ")";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, HumidityEvent> eventMap) {

        // 1st Temperature in the Critical Sequence
        HumidityEvent temp1 = (HumidityEvent) eventMap.get("temp1");
        // 2nd Temperature in the Critical Sequence
        HumidityEvent temp2 = (HumidityEvent) eventMap.get("temp2");
        // 3rd Temperature in the Critical Sequence
        HumidityEvent temp3 = (HumidityEvent) eventMap.get("temp3");
        // 4th Temperature in the Critical Sequence
        HumidityEvent temp4 = (HumidityEvent) eventMap.get("temp4");

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : " + temp1 + " > " + temp2 + " > " + temp3 + " > " + temp4 + "*");
        sb.append("\n***************************************");

        LOG.debug(sb.toString());
    }

    
}
