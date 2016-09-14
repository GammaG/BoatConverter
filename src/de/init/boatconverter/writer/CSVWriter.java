package de.init.boatconverter.writer;

import java.util.ArrayList;

import de.init.boatconverter.converter.Constants;
import de.init.boatconverter.pojos.CallHolder;

public class CSVWriter {

	public void createCSV(ArrayList<CallHolder> callholders) {

		// Write the first text line
		StringBuilder sbOut = new StringBuilder();
		sbOut.append(CsvUtil.quotedColumnSemicolon("Mitarbeiter"));
		sbOut.append(CsvUtil.quotedColumnSemicolon("Beschreibung"));
		sbOut.append(CsvUtil.quotedColumnSemicolon("Datum"));
		sbOut.append(CsvUtil.quotedColumnSemicolon("Dauer"));
		sbOut.append(CsvUtil.quotedColumnSemicolon("Startzeit"));
		sbOut.append(CsvUtil.quotedColumnSemicolon("Ende"));
		sbOut.append(CsvUtil.quotedColumnSemicolon("Interne Notiz"));
		sbOut.append(CsvUtil.quotedColumnSemicolon("Pruefung"));
		sbOut.append("\n");

		try {
			// fill in the information
			for (CallHolder holder : callholders) {
				sbOut.append(CsvUtil.quotedColumnSemicolon(holder.getPerson()));
				sbOut.append(CsvUtil.quotedColumnSemicolon(holder.getWorkDescription()));
				sbOut.append(CsvUtil.quotedColumnSemicolon(holder.getDate()));
				sbOut.append(CsvUtil.quotedColumnSemicolon(("" + holder.getTimeEffort()).replace(".", ",")));
				sbOut.append(CsvUtil.quotedColumnSemicolon((Constants.getTimeValueAsString(holder.getTimeFrom()))));
				sbOut.append(CsvUtil.quotedColumnSemicolon(Constants.getTimeValueAsString(holder.getTimeTo())));
				sbOut.append(CsvUtil.quotedColumnSemicolon(holder.getInternalNote()));
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
		sbOut.append(CsvUtil.quotedColumnAlternative("Mitarbeiter"));
		sbOut.append(CsvUtil.quotedColumnAlternative("Beschreibung"));
		sbOut.append(CsvUtil.quotedColumnAlternative("Datum"));
		sbOut.append(CsvUtil.quotedColumnAlternative("Startzeit_h"));
		sbOut.append(CsvUtil.quotedColumnAlternative("Startzeit_m"));
		sbOut.append(CsvUtil.quotedColumnAlternative("Endzeit_h"));
		sbOut.append(CsvUtil.quotedColumnAlternative("Endzeit_m"));
		sbOut.append("\n");

		try {
			// fill in the information
			for (CallHolder holder : callholders) {
				sbOut.append(CsvUtil.quotedColumn(holder.getPerson()));

				String task = holder.getWorkDescription();
				sbOut.append(CsvUtil.quotedColumn(task));
				sbOut.append(CsvUtil.quotedColumnAlternative(holder.getDate()));

				String time = Constants.getTimeValueAsString(holder.getTimeFrom());
				String[] times = time.split("[:]+");
				String hour = times[0];
				if (hour.length() == 1) {
					hour = "0" + hour;
				}
				String minute = times[1];
				sbOut.append(CsvUtil.quotedColumnAlternative(hour));
				sbOut.append(CsvUtil.quotedColumnAlternative(minute));

				time = Constants.getTimeValueAsString(holder.getTimeTo());
				times = time.split("[:]+");
				hour = times[0];
				if (hour.length() == 1) {
					hour = "0" + hour;
				}
				minute = times[1];

				sbOut.append(CsvUtil.quotedColumnAlternative(hour));
				sbOut.append(CsvUtil.quotedColumnAlternative(minute));

				sbOut.append("\n");
			}
			CsvUtil.saveFile("export", sbOut.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
