package org.ubicomp.event;

public class LuminosityEvent {

	private double luminosity;

	private String timeOfReading;

	public LuminosityEvent () {}

	public double getLuminosity() {
		return luminosity;
	}

	public String getTimeOfReading() {
		return timeOfReading;
	}
	
	public void setLuminosity(double luminosity) {
		this.luminosity = luminosity;
	}

	public void setTimeOfReading(String timeOfReading) {
		this.timeOfReading = timeOfReading;
	}

	@Override
	public String toString() {
		return "LuminosityEvent [" + luminosity + "%]";
	}

}