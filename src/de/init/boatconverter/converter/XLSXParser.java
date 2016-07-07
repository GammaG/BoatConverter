package de.init.boatconverter.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import de.init.boatconverter.pojos.CallHolder;

/**
 * Convert the format of the xlsx into the needed csv format
 * 
 * @author mseidler
 *
 */
public class XLSXParser {

	private ArrayList<CallHolder> callHolders = new ArrayList<CallHolder>();

	private ArrayList<ArrayList<Object>> sheetList;

	public XLSXParser(ArrayList<ArrayList<Object>> sheetList) {
		this.sheetList = sheetList;
	}

	public ArrayList<CallHolder> convertValues() {

		for (ArrayList<Object> valueList : sheetList) {
			int typeGuesser = 0;
			CallHolder callHolder = null;
			// skip first 14 useless information
			for (int counter = 14; counter < valueList.size(); counter++) {
				// every 6 values create a new CallHolder
				typeGuesser++;
				if (typeGuesser == 1) {
					callHolder = new CallHolder();
					continue;
				}
				// skip invalid values - no CallHolder will be added into the
				// list
				Object value = valueList.get(counter);
				if (value == null) {
					continue;
				}
				if (value instanceof String) {
					String temp = (String) value;
					callHolder.setWorkDescription(temp);
				} else if (value instanceof Double) {
					double temp = (double) value;
					if (typeGuesser == 2) {

						Calendar calendar = HSSFDateUtil.getJavaCalendar(temp);
						callHolder.setDate(calendar.getTime());
					}
					if (typeGuesser == 3) {
						callHolder.setTimeFrom(generateTimeValue(temp));
					}
					if (typeGuesser == 4) {
						callHolder.setTimeTo(generateTimeValue(temp));
					}
					if (typeGuesser == 5) {
						callHolder.setTimeBreak(generateTimeValue(temp));
					}
				}
				if (typeGuesser == 6) {
					typeGuesser = 0;
					callHolders.add(callHolder);
				}
			}
		}
		changeContentFormat();
		changeCallLenght();
		return callHolders;
	}

	private double generateTimeValue(double time) {
		double localTemp = time * 24;
		localTemp = roundTo2Decimals(localTemp);
		return localTemp;
	}

	private double roundTo2Decimals(double val) {
		double value;
		try {
			DecimalFormat df2 = new DecimalFormat("###.##");
			value = Double.valueOf(df2.format(val));
		} catch (NumberFormatException e) {
			return val;
		}
		return value;
	}

	private void changeContentFormat() {
		for (CallHolder holder : callHolders) {
			holder.setTimeEffort(holder.getTimeTo() - holder.getTimeFrom() - holder.getTimeBreak());
			String temp = holder.getWorkDescription();
			holder.setWorkDescription(removeInvalidCharacters(temp));
		}
	}

	private String removeInvalidCharacters(String text) {
		if (text == null) {
			return "";
		}
		String local = text;
		local = local.replaceAll("Ü", "Ue");
		local = local.replaceAll("ü", "ue");
		local = local.replaceAll("Ä", "Ae");
		local = local.replaceAll("ä", "ae");
		local = local.replaceAll("Ö", "Oe");
		local = local.replaceAll("ö", "oe");
		local = local.replaceAll("ß", "ss");
		return local;

	}

	/**
	 * Changes the length of the breaks and splits tasks into max 6 hours
	 */
	private void changeCallLenght() {
		ArrayList<CallHolder> localList = new ArrayList<CallHolder>();
		for (CallHolder holder : callHolders) {

			if (holder.getTimeBreak() < 1) {
				double originalBreakTime = holder.getTimeBreak();
				double toAdd = 1 - originalBreakTime;
				holder.setTimeBreak(1.0);
				holder.setTimeTo(holder.getTimeTo() + toAdd);
			}

			if (holder.getTimeEffort() > 6) {
				CallHolder localHolder = new CallHolder();
				localHolder.setDate(holder.getDate());
				localHolder.setWorkDescription(holder.getWorkDescription());
				localHolder.setTimeBreak(0);

				double oldToValue = holder.getTimeTo();
				double toValue = oldToValue;
				while ((toValue - holder.getTimeFrom()) >= 6) {
					toValue--;
				}

				holder.setTimeTo(toValue);
				holder.setTimeEffort(holder.getTimeTo() - holder.getTimeFrom() - holder.getTimeBreak());
				localHolder.setTimeFrom(toValue + holder.getTimeBreak());
				localHolder.setTimeTo(oldToValue);
				localHolder.setTimeEffort(oldToValue - toValue);

				localList.add(holder);
				localList.add(localHolder);
			} else {
				localList.add(holder);
			}
		}
		callHolders = localList;
	}
}
