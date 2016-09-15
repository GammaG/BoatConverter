package de.init.boatconverter.converter;

import java.util.ArrayList;

import de.init.boatconverter.pojos.CallHolder;
import de.init.boatconverter.usage.Constants;

/**
 * Convert the format of the xlsx into the needed csv format
 * 
 * @author mseidler
 *
 */
public class CSVParser {

	private int PERSON = -1;
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
		return new CallLengthConverter(callHolders).changeCallLenght();

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
		if (PERSON == -1 | WORKDESCRIPTION == -1 | DATE == -1 | EFFORT == -1 | TIMEFROM == -1 | TIMETO == -1) {
			Constants.dialog("Not all needed field have been given.\nNeeded are:\nMitarbeiter\nPfad\nBeschreibung\nDatum\nDauer\nStartzeit\nEnde\n");
			System.exit(1);
		}

	}

	private void changeContentFormat() {
		for (CallHolder holder : callHolders) {
			String temp = holder.getWorkDescription();
			holder.setWorkDescription(Constants.removeInvalidCharacters(temp));
		}
	}

}
