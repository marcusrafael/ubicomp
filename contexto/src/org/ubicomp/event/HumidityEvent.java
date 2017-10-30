package org.ubicomp.event;

public class HumidityEvent {

	private double humidity;

	private String timeOfReading;

	public HumidityEvent() {}

	public double getHumidity() {
		return humidity;
	}

	public String getTimeOfReading() {
		return timeOfReading;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public void setTimeOfReading(String timeOfReading) {
		this.timeOfReading = timeOfReading;
	}

	@Override
	public String toString() {
		return "HumidityEvent [" + humidity + "%]";
	}

}