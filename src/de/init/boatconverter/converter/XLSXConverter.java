package de.init.boatconverter.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import de.init.boatconverter.pojos.CallHolder;
import de.init.boatconverter.usage.Constants;

public class XLSXConverter {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	private ArrayList<CallHolder> callHolders;

	public XLSXConverter(ArrayList<CallHolder> callHolders) {
		this.callHolders = callHolders;
	}

	public ArrayList<CallHolder> formatToValidForm() {

		ArrayList<CallHolder> localHolders = new ArrayList<CallHolder>();

		for (CallHolder callHolder : callHolders) {

			// replace the Date String
			String temp = callHolder.getDate();
			if (temp == null) {
				continue;
			}
			double dateDouble = new Double(temp);
			Calendar calendar = HSSFDateUtil.getJavaCalendar(dateDouble);
			String date = sdf.format(calendar.getTime());
			callHolder.setDate(date);

			// replace invalid characters
			String task = Constants.removeInvalidCharacters(callHolder.getWorkDescription());
			callHolder.setWorkDescription(task);

			// add missing effort time values
			callHolder.setTimeEffort(callHolder.getTimeTo() - callHolder.getTimeFrom());

			// change time layout
			double timeFrom = callHolder.getTimeFrom();
			timeFrom = generateTimeValue(timeFrom);
			timeFrom = round(timeFrom, 2);
			callHolder.setTimeFrom(timeFrom);

			// change time layout
			double timeTo = callHolder.getTimeTo();
			timeTo = generateTimeValue(timeTo);
			timeTo = round(timeTo, 2);
			callHolder.setTimeTo(timeTo);

			// change time layout
			double timeEffort = callHolder.getTimeEffort();
			timeEffort = generateTimeValue(timeEffort);
			timeEffort = round(timeEffort, 2);
			callHolder.setTimeEffort(timeEffort);

			localHolders.add(callHolder);
		}
		return new CallLengthConverter(localHolders).changeCallLenght();
	}

	private double generateTimeValue(double time) {
		double localTemp = time * 24;
		return localTemp;
	}

	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
