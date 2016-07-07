package de.init.boatconverter.converter;

import java.text.SimpleDateFormat;

public class Constants {

	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	public static String getTimeValueAsString(double time) {
		String value = "";

		long iPart;
		double fPart;

		// Get user input
		iPart = (long) time;
		fPart = time - iPart;

		value += "" + fPart;

		switch (value) {
		case "0.0":
			value = iPart + ":00";
			break;

		case "0.25":
			value = iPart + ":15";
			break;

		case "0.5":
			value = iPart + ":30";
			break;

		case "0.75":
			value = iPart + ":45";
			break;

		}
		return value;
	}

	public static double generateDoubleFromTimeString(String time) {
		double value = 0;

		String[] temp = time.split("[:]+");

		String iPart = temp[0];
		String fPart = temp[1];

		value += new Double(iPart);

		switch (fPart) {

		case "15":
			value += 0.25;
			break;

		case "30":
			value += 0.5;
			break;

		case "45":
			value += 0.75;
			break;

		}

		return value;
	}

}
