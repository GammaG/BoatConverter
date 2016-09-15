package de.init.boatconverter;

import java.io.IOException;
import java.util.ArrayList;

import de.init.boatconverter.converter.CSVParser;
import de.init.boatconverter.converter.XLSXConverter;
import de.init.boatconverter.pojos.CallHolder;
import de.init.boatconverter.reader.ExcelReader;
import de.init.boatconverter.reader.FileReader;
import de.init.boatconverter.usage.Constants;
import de.init.boatconverter.writer.CSVWriter;

public class Main {

	public static void main(String[] args) {
		String path = "";

		if (args.length == 0) {
			showErrorMessageAndExit();
		} else {
			path = args[0];
			if (path.equals("-d")) {
				Constants.DIALOG_STATUS = Constants.Dialog.SHOW_DIALOG;
				try {
					path = args[1];
				} catch (Exception e) {
					showErrorMessageAndExit();
				}
			}
		}

		try {
			ArrayList<CallHolder> callholders = null;
			if (path.endsWith(".csv")) {
				ArrayList<ArrayList<String>> sheetList = new FileReader().readFile(path);
				callholders = new CSVParser(sheetList).convertValues();
			} else if (path.endsWith(".xlsx")) {
				callholders = new ExcelReader().readTheXLSXFile(path);
				XLSXConverter converter = new XLSXConverter(callholders);
				callholders = converter.formatToValidForm();
			}

			boolean valuesAreValid = true;
			ArrayList<CallHolder> buggedHolder = new ArrayList<CallHolder>();
			for (CallHolder holder : callholders) {
				if (!holder.checkIfValuesAreValid()) {
					valuesAreValid = false;
					buggedHolder.add(holder);
				}

			}

			CSVWriter csvWriter = new CSVWriter();
			if (callholders != null & valuesAreValid) {
				csvWriter.createCSV(callholders);
				csvWriter.createCSVForParsing(callholders);
			} else {

				StringBuilder builder = new StringBuilder();
				builder.append("The following items are invalid:\n");
				for (CallHolder holder : buggedHolder) {
					builder.append(holder.toString() + "\n");
				}
				Constants.dialog(builder.toString());
				System.exit(1);
			}

		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		System.exit(0);
	}

	private static void showErrorMessageAndExit() {
		System.out.println("Please give a .csv or .xlsx file as parameter.");
		System.exit(1);
	}
}
