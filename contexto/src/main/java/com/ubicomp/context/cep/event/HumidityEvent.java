package com.ubicomp.context.cep.event;

import java.util.Date;

public class HumidityEvent {

    /** Humidity in Celcius. */
    private double humidity;
    
    /** Time temerature reading was taken. */
    private Date timeOfReading;
    
    /**
     * Humidity constructor.
     * @param humidity Humidity in Celsius
     * @param timeOfReading Time of Reading
     */
    public HumidityEvent(double humidity, Date timeOfReading) {
        this.humidity = humidity;
        this.timeOfReading = timeOfReading;
    }

    /**
     * Get the Humidity.
     * @return Humidity in Celsius
     */
    public double getHumidity() {
        return humidity;
    }
       
    /**
     * Get time Humidity reading was taken.
     * @return Time of Reading
     */
    public Date getTimeOfReading() {
        return timeOfReading;
    }

    @Override
    public String toString() {
        return "HumidityEvent [" + humidity + "%]";
    }

}
