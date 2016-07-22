package de.init.boatconverter.converter;

import java.util.ArrayList;

import de.init.boatconverter.pojos.CallHolder;

/**
 * Convert the format of the xlsx into the needed csv format
 * 
 * @author mseidler
 *
 */
public class CSVParser {

	private int PERSON = -1;
	private int PRICECLASS = -1;
	private int WORKDESCRIPTION = -1;
	private int DATE = -1;
	private int EFFORT = -1;
	private int TIMEFROM = -1;
	private int TIMETO = -1;

	private ArrayList<CallHolder> callHolders = new ArrayList<CallHolder>();

	private ArrayList<ArrayList<String>> sheetList;

	public CSVParser(ArrayList<ArrayList<String>> sheetList) {
		this.sheetList = sheetList;
	}

	public ArrayList<CallHolder> convertValues() {

		for (ArrayList<String> line : sheetList) {
			boolean skip = false;
			CallHolder callHolder = new CallHolder();
			for (int i = 0; i < line.size(); i++) {
				String element = line.get(i);
				element = element.replaceAll("\"", "");
				// skip first line
				if (element.equals("Mitarbeiter") || skip) {
					if (!skip)
						matchRows(line);
					skip = true;
					continue;
				}

				try {
					if (i == PERSON)
						callHolder.setPerson(element);
					else if (i == PRICECLASS)
						callHolder.setPriceclass(parsePriceLevel(element));
					else if (i == WORKDESCRIPTION)
						callHolder.setWorkDescription(element);
					else if (i == DATE)
						callHolder.setDate(element);
					else if (i == EFFORT) {
						element = element.replaceAll(",", ".");
						callHolder.setTimeEffort(new Double(element));
					} else if (i == TIMEFROM)
						callHolder.setTimeFrom(Constants.generateDoubleFromTimeString(element));
					else if (i == TIMETO)
						callHolder.setTimeTo(Constants.generateDoubleFromTimeString(element));
				} catch (Exception e) {
					Constants
							.dialog("There have been parsing errors. Have you not filled out all blanks or not removed the emtpy rows?\n Also have you replaced all ; with . ?");
					System.out.println(e.getMessage());
					System.exit(1);
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
		String result = "Preisstufe ";
		try {
			String[] strings = element.split("[ ]+");
			result += strings[strings.length - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore it
		}
		return result;

	}

	private void matchRows(ArrayList<String> list) {
		ArrayList<String> localList = new ArrayList<String>();
		for (String s : list) {
			s = s.replaceAll("\"", "");
			localList.add(s);
		}

		for (String s : localList) {
			switch (s) {
			case "Mitarbeiter":
				PERSON = localList.indexOf(s);
				break;
			case "Pfad":
				PRICECLASS = localList.indexOf(s);
				break;
			case "Beschreibung":
				WORKDESCRIPTION = localList.indexOf(s);
				break;
			case "Datum":
				DATE = localList.indexOf(s);
				break;
			case "Dauer":
				EFFORT = localList.indexOf(s);
				break;
			case "Startzeit":
				TIMEFROM = localList.indexOf(s);
				break;
			case "Ende":
				TIMETO = localList.indexOf(s);
				break;
			}
		}
		if (PERSON == -1 | PRICECLASS == -1 | WORKDESCRIPTION == -1 | DATE == -1 | EFFORT == -1 | TIMEFROM == -1 | TIMETO == -1) {
			Constants.dialog("Not all needed field have been given.\nNeeded are:\nMitarbeiter\nPfad\nBeschreibung\nDatum\nDauer\nStartzeit\nEnde\n");
			System.exit(1);
		}

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
				holder.setTimeEffort(holder.getTimeTo() - holder.getTimeFrom());
				localHolder.setTimeFrom(toValue + holder.getTimeBreak());
				localHolder.setTimeTo(oldToValue);
				localHolder.setTimeEffort(oldToValue - (toValue + holder.getTimeBreak()));
				localHolder.setPerson(holder.getPerson());
				localHolder.setPriceclass(holder.getPriceclass());

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
