package com.ubicomp.context.cep.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.ubicomp.context.cep.event.TemperatureEvent;
import com.ubicomp.context.cep.input.EsperioHttpInputAdapter;
import com.ubicomp.context.cep.subscriber.StatementSubscriber;

@Component
@Scope(value = "singleton")
public class TemperatureEventHandler implements InitializingBean{

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(TemperatureEventHandler.class);

    /** Esper service */
    private EPServiceProvider epService;
    private EPStatement criticalEventStatement;
    private EPStatement warningEventStatement;
    private EPStatement attentionEventStatement;
    private EsperioHttpInputAdapter esperioHttpInputAdapter;

    @Autowired
    @Qualifier("criticalEventSubscriber")
    private StatementSubscriber criticalEventSubscriber;

    @Autowired
    @Qualifier("warningEventSubscriber")
    private StatementSubscriber warningEventSubscriber;

    @Autowired
    @Qualifier("attentionEventSubscriber")
    private StatementSubscriber attentionEventSubscriber;

    /**
     * Configure Esper Statement(s).
     */
    public void initService() {

        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.ubicomp.context.cep.event");
        esperioHttpInputAdapter.epService = EPServiceProviderManager.getDefaultProvider(config);
        epService = esperioHttpInputAdapter.epService;

        createCriticalTemperatureCheckExpression();
        createWarningTemperatureCheckExpression();
        createAttentionTemperatureCheckExpression();

    }

    // EPL check 4 events critical if the last event is 1.5x greater than the first event     
    private void createCriticalTemperatureCheckExpression() {
        
        LOG.debug("create Critical Temperature Check Expression");
        criticalEventStatement = epService.getEPAdministrator().createEPL(criticalEventSubscriber.getStatement());
        criticalEventStatement.setSubscriber(criticalEventSubscriber);
    }

    // EPL to check 2 events greater than threshold
    private void createWarningTemperatureCheckExpression() {

        LOG.debug("create Warning Temperature Check Expression");
        warningEventStatement = epService.getEPAdministrator().createEPL(warningEventSubscriber.getStatement());
        warningEventStatement.setSubscriber(warningEventSubscriber);
    }

    
    // EPL to monitor the average temperature every 10 seconds
     
    private void createAttentionTemperatureCheckExpression() {

        LOG.debug("create Attention Temperature Check Expression");
        attentionEventStatement = epService.getEPAdministrator().createEPL(attentionEventSubscriber.getStatement());
        attentionEventStatement.setSubscriber(attentionEventSubscriber);
    }

    
    public void handle(TemperatureEvent event) {

        LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

    @Override
    public void afterPropertiesSet() {
        
        LOG.debug("Configuring..");
        initService();
    }
}
