package org.ubicomp.event;


public class TemperatureEvent {

    /** Temperature in Celcius. */
    private double temperature;
    
    /** Time temerature reading was taken. */
    private String timeOfReading;
    
    /**
     * Temperature constructor.
     * @param temperature Temperature in Celsius
     * @param timeOfReading Time of Reading
     */
    public TemperatureEvent(){}
    public TemperatureEvent(double temperature){
    	this.temperature = temperature;
    }

    /**
     * Get the Temperature.
     * @return Temperature in Celsius
     */
    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public void setTimeOfReading(String timeOfReading) {
        this.timeOfReading = timeOfReading;
    }
       
    /**
     * Get time Temperature reading was taken.
     * @return Time of Reading
     */
    public String getTimeOfReading() {
        return timeOfReading;
    }

    @Override
    public String toString() {
        return "TemperatureEvent [" + temperature + "C]";
    }

}
