package de.init.boatconverter.writer;

import java.util.ArrayList;

import de.init.boatconverter.converter.Constants;
import de.init.boatconverter.pojos.CallHolder;

public class CSVWriter {

	public void createCSV(ArrayList<CallHolder> callholders) {

		// Write the first text line
		StringBuilder sbOut = new StringBuilder();
		sbOut.append(CsvUtil.quotedColumn("Mitarbeiter"));
		sbOut.append(CsvUtil.quotedColumn("Aufgabe"));
		sbOut.append(CsvUtil.quotedColumn("Beschreibung"));
		sbOut.append(CsvUtil.quotedColumn("Datum"));
		sbOut.append(CsvUtil.quotedColumn("Dauer"));
		sbOut.append(CsvUtil.quotedColumn("Startzeit"));
		sbOut.append(CsvUtil.quotedColumn("Ende"));
		sbOut.append(CsvUtil.quotedColumn("Endzeit"));
		sbOut.append(CsvUtil.quotedColumn("Interne Notiz"));
		sbOut.append(CsvUtil.quotedColumn("Pruefung"));
		sbOut.append("\n");

		try {
			// fill in the information
			for (CallHolder holder : callholders) {
				sbOut.append(CsvUtil.quotedColumn(holder.getPerson()));
				sbOut.append(CsvUtil.quotedColumn(holder.getPriceclass()));
				sbOut.append(CsvUtil.quotedColumn(holder.getWorkDescription()));
				sbOut.append(CsvUtil.quotedColumn(holder.getDate()));
				sbOut.append(CsvUtil.quotedColumn(Constants.getTimeValueAsString(holder.getTimeEffort())));
				sbOut.append(CsvUtil.quotedColumn(Constants.getTimeValueAsString(holder.getTimeFrom())));
				sbOut.append(CsvUtil.quotedColumn(Constants.getTimeValueAsString(holder.getTimeTo())));
				sbOut.append(CsvUtil.quotedColumn(holder.getInternalNote()));
				sbOut.append("\n");
			}
			CsvUtil.saveFile("Leistungsnachweis", sbOut.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
