package de.init.boatconverter.writer;

import java.util.ArrayList;

import de.init.boatconverter.converter.Constants;
import de.init.boatconverter.pojos.CallHolder;

public class CSVWriter {

	public void createCSV(ArrayList<CallHolder> callholders) {

		// Write the first text line
		StringBuilder sbOut = new StringBuilder();
		sbOut.append(CsvUtil.quotedColumn("Mitarbeiter"));
		sbOut.append(CsvUtil.quotedColumn("Beschreibung"));
		sbOut.append(CsvUtil.quotedColumn("Datum"));
		sbOut.append(CsvUtil.quotedColumn("Dauer"));
		sbOut.append(CsvUtil.quotedColumn("Startzeit"));
		sbOut.append(CsvUtil.quotedColumn("Endzeit"));
		sbOut.append(CsvUtil.quotedColumn("Interne Notiz"));
		sbOut.append(CsvUtil.quotedColumn("Pruefung"));
		sbOut.append("\n");

		try {
			// fill in the information
			for (CallHolder holder : callholders) {
				sbOut.append(CsvUtil.quotedColumn(holder.getPerson()));
				sbOut.append(CsvUtil.quotedColumn(holder.getWorkDescription()));
				sbOut.append(CsvUtil.quotedColumn(holder.getDate()));
				sbOut.append(CsvUtil.quotedColumn(("" + holder.getTimeEffort()).replace(".", ",")));
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

	public void createCSVForParsing(ArrayList<CallHolder> callholders) {

		// Write the first text line
		StringBuilder sbOut = new StringBuilder();
		sbOut.append(CsvUtil.quotedColumn("Mitarbeiter"));
		sbOut.append(CsvUtil.quotedColumn("Beschreibung"));
		sbOut.append(CsvUtil.quotedColumn("Datum"));
		sbOut.append(CsvUtil.quotedColumn("Startzeit_h"));
		sbOut.append(CsvUtil.quotedColumn("Startzeit_m"));
		sbOut.append(CsvUtil.quotedColumn("Endzeit_h"));
		sbOut.append(CsvUtil.quotedColumn("Endzeit_m"));
		sbOut.append("\n");

		try {
			// fill in the information
			for (CallHolder holder : callholders) {
				sbOut.append(CsvUtil.quotedColumn(holder.getPerson()));

				String task = holder.getWorkDescription();
				sbOut.append(CsvUtil.quotedColumn(task));
				sbOut.append(CsvUtil.quotedColumn(holder.getDate()));

				String time = Constants.getTimeValueAsString(holder.getTimeFrom());
				String[] times = time.split("[:]+");
				String hour = times[0];
				if (hour.length() == 1) {
					hour = "0" + hour;
				}
				String minute = times[1];
				sbOut.append(CsvUtil.quotedColumn(hour));
				sbOut.append(CsvUtil.quotedColumn(minute));

				time = Constants.getTimeValueAsString(holder.getTimeTo());
				times = time.split("[:]+");
				hour = times[0];
				if (hour.length() == 1) {
					hour = "0" + hour;
				}
				minute = times[1];

				sbOut.append(CsvUtil.quotedColumn(hour));
				sbOut.append(CsvUtil.quotedColumn(minute));

				sbOut.append("\n");
			}
			CsvUtil.saveFile("export", sbOut.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
