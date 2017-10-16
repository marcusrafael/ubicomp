package com.ubicomp.context.cep.util;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ubicomp.context.cep.event.LuminosityEvent;
import com.ubicomp.context.cep.handler.FireEventHandler;

@Component
public class RandomLuminosityEventGenerator {

    /** Logger */
    private static Logger LOG = LoggerFactory.getLogger(RandomLuminosityEventGenerator.class);

    /** The LuminosityEventHandler - wraps the Esper engine and processes the Events  */
    @Autowired
    private FireEventHandler luminosityEventHandler;

    public void startSendingLuminosityReadings(final long noOfLuminosityEvents) {

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());
                
                int count = 0;
                while (count < noOfLuminosityEvents) {
                    LuminosityEvent le = new LuminosityEvent(new Random().nextInt(100)/100.0, new Date());
                    luminosityEventHandler.handle(le);
                    count++;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        LOG.error("Thread Interrupted", e);
                    }
                }

            }
        });
    }

    
    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n**********************");
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT - LUMINOSITY ARE RANDOM SO MAY TAKE");
        sb.append("\n**********************\n");
        return sb.toString();
    }
}