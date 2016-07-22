package de.init.boatconverter.converter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import de.init.boatconverter.pojos.CallHolder;

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
			timeFrom = roundTo2Decimals(timeFrom);
			callHolder.setTimeFrom(timeFrom);

			// change time layout
			double timeTo = callHolder.getTimeTo();
			timeTo = generateTimeValue(timeTo);
			timeTo = roundTo2Decimals(timeTo);
			callHolder.setTimeTo(timeTo);

			// change time layout
			double timeEffort = callHolder.getTimeEffort();
			timeEffort = generateTimeValue(timeEffort);
			timeEffort = roundTo2Decimals(timeEffort);
			callHolder.setTimeEffort(timeEffort);

			localHolders.add(callHolder);
		}
		return new CallLengthConverter(localHolders).changeCallLenght();
	}

	private double generateTimeValue(double time) {
		double localTemp = time * 24;
		localTemp = roundTo2Decimals(localTemp);
		return localTemp;
	}

	private double roundTo2Decimals(double val) {
		double value;
		try {
			DecimalFormat df2 = new DecimalFormat("###,##");
			value = Double.valueOf(df2.format(val));
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return val;
		}
		return value;
	}

}
