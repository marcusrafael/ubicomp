package org.ubicomp.listener;

public class ThresholdersValues {
	private static final String TEMPERATURE_THRESHOLD = "40";
	private static final String WARNING_TEMPERATURE_THRESHOLD = "60";
	private static final String CRITICAL_TEMPERATURE_THRESHOLD = "80";
	private static final String CRITICAL_TEMPERATURE_MULTIPLIER = "1.5";
	private static final String HUMIDITY_THRESHOLD = "0.30";
	private static final String LUMINOSITY_THRESHOLD = "0.80";
	
	
	public ThresholdersValues() {}

	
	
	public static String getCriticalTemperatureThreshold() {
		return CRITICAL_TEMPERATURE_THRESHOLD;
	}

	public static String getCriticalTemperatureMultiplier() {
		return CRITICAL_TEMPERATURE_MULTIPLIER;
	}

	public static String getTemperatureThreshold() {
		return TEMPERATURE_THRESHOLD;
	}

	public static String getHumidityThreshold() {
		return HUMIDITY_THRESHOLD;
	}

	public static String getLuminosityThreshold() {
		return LUMINOSITY_THRESHOLD;
	}

	public static String getWarningTemperatureThreshold() {
		return WARNING_TEMPERATURE_THRESHOLD;
	}
	
	
}