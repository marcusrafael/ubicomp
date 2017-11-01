package org.ubicomp.event;

import java.io.FileNotFoundException;

import org.ubicomp.util.CSVFile;

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
		try {
			CSVFile.escrevaHumidity("Humidity , " + humidity);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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