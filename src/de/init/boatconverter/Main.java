package de.init.boatconverter;

import java.io.IOException;
import java.util.ArrayList;

import de.init.boatconverter.converter.CSVParser;
import de.init.boatconverter.converter.Constants;
import de.init.boatconverter.pojos.CallHolder;
import de.init.boatconverter.reader.ExcelReader;
import de.init.boatconverter.reader.FileReader;
import de.init.boatconverter.writer.CSVWriter;

public class Main {

	public static void main(String[] args) {
		String path = "";

		if (args.length == 0) {
			System.out.println("Please give a .csv or .xlsx file as parameter.");
			System.exit(0);
		} else {
			path = args[0];
		}

		try {
			ArrayList<CallHolder> callholders = null;
			if (path.endsWith(".csv")) {
				ArrayList<ArrayList<String>> sheetList = new FileReader().readFile(path);
				callholders = new CSVParser(sheetList).convertValues();
			} else if (path.endsWith(".xlsx")) {
				ExcelReader excelReader = new ExcelReader();
				excelReader.readTheXLSXFile(path);
			}
			if (callholders != null)
				new CSVWriter().createCSV(callholders);
			else {
				Constants.dialog("No information were read, was the given file format .csv or .xlsx?");
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
