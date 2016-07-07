package de.init.boatconverter.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.init.boatconverter.pojos.CallHolder;

/**
 * Convert the format of the xlsx into the needed csv format
 * 
 * @author mseidler
 *
 */
public class XLSXParser {

	private ArrayList<CallHolder> callHolders = new ArrayList<CallHolder>();

	private ArrayList<ArrayList<String>> sheetList;

	public XLSXParser(ArrayList<ArrayList<String>> sheetList) {
		this.sheetList = sheetList;
	}

	public ArrayList<CallHolder> convertValues() {

		for (ArrayList<String> line : sheetList) {
			boolean skip = false;
			CallHolder callHolder = new CallHolder();
			for (int i = 0; i < line.size(); i++) {
				String element = line.get(i);
				// skip first line
				if (element.equals("Mitarbeiter") || skip) {
					skip = true;
					continue;
				}

				switch (i) {
				case 0:
					callHolder.setPerson(element);
					break;
				case 1:
					callHolder.setPriceclass(parsePriceLevel(element));
					break;
				case 4:
					callHolder.setWorkDescription(element);
					break;
				case 5:
					callHolder.setDate(element);
					break;
				case 6:
					element = element.replace(",", ".");
					callHolder.setTimeEffort(new Double(element));
					break;
				case 7:
					callHolder.setTimeFrom(Constants.generateDoubleFromTimeString(element));
					break;
				case 8:
					callHolder.setTimeTo(Constants.generateDoubleFromTimeString(element));
					break;
				}
			}
			if (skip) {
				continue;
			}
			callHolders.add(callHolder);
		}
		changeContentFormat();
		changeCallLenght();
		return callHolders;
	}

	private String parsePriceLevel(String element) {
		String[] strings = element.split("[ ]+");
		String result = strings[strings.length - 2] + " " + strings[strings.length - 1];
		return result;

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

			if (holder.getTimeBreak() < 1 && holder.getTimeEffort() >= 6) {
				double originalBreakTime = holder.getTimeBreak();
				double toAdd = 1 - originalBreakTime;
				holder.setTimeBreak(1.0);
				holder.setTimeTo(holder.getTimeTo() + toAdd);
				holder.setInternalNote("Buchung >= 6 Stunden -> 1 Stunde Pausenzeit hinzugefuegt und Zeiten veraendert");
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

				String message = "Buchung > 6 Stunden -> Aufgesplitted in zwei Buchungen, Zeiten fuer Pause angepasst";
				holder.setInternalNote(message);
				localHolder.setInternalNote(message);

				localList.add(holder);
				localList.add(localHolder);

			} else {
				localList.add(holder);
			}
		}
		callHolders = localList;
	}
}
