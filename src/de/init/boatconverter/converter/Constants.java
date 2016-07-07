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

}
