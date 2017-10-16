package com.ubicomp.context.cep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ubicomp.context.cep.input.EsperioHttpInputAdapter;
import com.ubicomp.context.cep.util.RandomHumidityEventGenerator;
import com.ubicomp.context.cep.util.RandomLuminosityEventGenerator;
import com.ubicomp.context.cep.util.RandomTemperatureEventGenerator;

public class Main {

    private static Logger LOG = LoggerFactory.getLogger(Main.class);

    
    public static void main(String[] args) throws Exception {

        LOG.debug("Starting...");

        long noOfEvents = 1000;

        if (args.length != 1) {
            LOG.debug("No override of number of events detected - defaulting to " + noOfEvents + " events.");
        } else {
            noOfEvents = Long.valueOf(args[0]);
        }

        // Load spring config
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" });
        BeanFactory factory = (BeanFactory) appContext;

        // Start Demo
        RandomTemperatureEventGenerator tGenerator = (RandomTemperatureEventGenerator) factory.getBean("tEventGenerator");
        tGenerator.startSendingTemperatureReadings(noOfEvents);
        
        RandomLuminosityEventGenerator lGenerator = (RandomLuminosityEventGenerator) factory.getBean("lEventGenerator");
        lGenerator.startSendingLuminosityReadings(noOfEvents);
        
        RandomHumidityEventGenerator hGenerator = (RandomHumidityEventGenerator) factory.getBean("hEventGenerator");
        hGenerator.startSendingHumidityReadings(noOfEvents);
        
        
        EsperioHttpInputAdapter app = new EsperioHttpInputAdapter();
        app.run();
        

    }

}
