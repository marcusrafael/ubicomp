package com.ubicomp.context.cep.event;

import java.util.Date;

public class TemperatureEvent {

    /** Temperature in Celcius. */
    private double temperature;
    
    /** Time temerature reading was taken. */
    private Date timeOfReading;
    
    /**
     * Temperature constructor.
     * @param temperature Temperature in Celsius
     * @param timeOfReading Time of Reading
     */
    public TemperatureEvent(double temperature, Date timeOfReading) {
        this.temperature = temperature;
        this.timeOfReading = timeOfReading;
    }

    /**
     * Get the Temperature.
     * @return Temperature in Celsius
     */
    public double getTemperature() {
        return temperature;
    }
       
    /**
     * Get time Temperature reading was taken.
     * @return Time of Reading
     */
    public Date getTimeOfReading() {
        return timeOfReading;
    }

    @Override
    public String toString() {
        return "TemperatureEvent [" + temperature + "C]";
    }

}
