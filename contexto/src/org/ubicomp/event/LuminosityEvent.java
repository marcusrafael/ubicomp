package org.ubicomp.event;

import java.io.FileNotFoundException;

import org.ubicomp.util.CSVFile;

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
		
		try {
			CSVFile.escrevaLuminosity("Luminosity , " + luminosity);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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