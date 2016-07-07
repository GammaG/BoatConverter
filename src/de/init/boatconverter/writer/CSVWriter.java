package de.init.boatconverter.writer;

import java.util.ArrayList;

import de.init.boatconverter.converter.Constants;
import de.init.boatconverter.pojos.CallHolder;

public class CSVWriter {

	public void createCSV(ArrayList<CallHolder> callholders) {

		// Write the first text line
		StringBuilder sbOut = new StringBuilder();
		sbOut.append(CsvUtil.quotedColumn("Projektgruppe/Gruppe"));
		sbOut.append(CsvUtil.quotedColumn("Projekt"));
		sbOut.append(CsvUtil.quotedColumn("UP/AP"));
		sbOut.append(CsvUtil.quotedColumn("Aufgabe"));
		sbOut.append(CsvUtil.quotedColumn("Aufgaben-ID"));
		sbOut.append(CsvUtil.quotedColumn("Buchungsdatum"));
		sbOut.append(CsvUtil.quotedColumn("Anfangszeit"));
		sbOut.append(CsvUtil.quotedColumn("Endzeit"));
		sbOut.append(CsvUtil.quotedColumn("Beschreibung"));
		sbOut.append(CsvUtil.quotedColumn("Person"));
		sbOut.append(CsvUtil.quotedColumn("Personalnummer"));
		sbOut.append(CsvUtil.quotedColumn("Mitarbeiter-ID"));
		sbOut.append("\n");

		try {
			// fill in the information
			for (CallHolder holder : callholders) {

				sbOut.append(CsvUtil.quotedColumn(Constants.SIMPLE_DATE_FORMAT.format(holder.getDate())));
				sbOut.append(CsvUtil.quotedColumn(Constants.getTimeValueAsString(holder.getTimeFrom())));
				sbOut.append(CsvUtil.quotedColumn(Constants.getTimeValueAsString(holder.getTimeTo())));
				sbOut.append(CsvUtil.quotedColumn(holder.getWorkDescription()));

				sbOut.append("\n");

			}
			CsvUtil.saveFile("Leistungsnachweis", sbOut.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage() + "\n Have all sheets been added into the properties file?");
		}
	}
}
