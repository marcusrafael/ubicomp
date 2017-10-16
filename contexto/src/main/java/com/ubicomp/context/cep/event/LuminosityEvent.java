package com.ubicomp.context.cep.event;

import java.util.Date;

public class LuminosityEvent {

    /** Luminosity in percentage. */
    private double luminosity;
    
    /** Time luminosity reading was taken. */
    private Date timeOfReading;
    
    /**
     * Temperature constructor.
     * @param luminosity Luminosity in percentage
     * @param timeOfReading Time of Reading
     */
    public LuminosityEvent (double luminosity, Date timeOfReading) {
        this.luminosity = luminosity;
        this.timeOfReading = timeOfReading;
    }

    /**
     * Get the Luminosity.
     * @return Luminosity in percentage
     */
    public double getLuminosity() {
        return luminosity;
    }
       
    /**
     * Get time Luminosity reading was taken.
     * @return Time of Reading
     */
    public Date getTimeOfReading() {
        return timeOfReading;
    }

    @Override
    public String toString() {
        return "LuminosityEvent [" + luminosity + "%]";
    }

}