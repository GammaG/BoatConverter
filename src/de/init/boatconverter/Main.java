package de.init.boatconverter;

import java.io.IOException;
import java.util.ArrayList;

import de.init.boatconverter.converter.XLSXParser;
import de.init.boatconverter.pojos.CallHolder;
import de.init.boatconverter.reader.FileReader;
import de.init.boatconverter.writer.CSVWriter;

public class Main {

	public static void main(String[] args) {
		String path = "";

		if (args.length == 0) {
			System.out.println("Please give a csv file as parameter.");
			System.exit(0);
		} else {
			path = args[0];
		}

		if (!path.endsWith(".csv")) {
			System.out.println("Only CSV files are supported as input! Please save your file as csv and try again.");
			System.exit(1);
		}

		try {

			ArrayList<ArrayList<String>> sheetList = new FileReader().readFile(path);
			ArrayList<CallHolder> callholders = new XLSXParser(sheetList).convertValues();

			new CSVWriter().createCSV(callholders);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
