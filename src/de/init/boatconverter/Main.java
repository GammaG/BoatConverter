package de.init.boatconverter;

import java.io.IOException;
import java.util.ArrayList;

import de.init.boatconverter.converter.XLSXParser;
import de.init.boatconverter.pojos.CallHolder;
import de.init.boatconverter.reader.ReadExcel;

public class Main {

	public static void main(String[] args) {
		String path = "";
		if (args[0].equals("")) {
			System.out.println("Please give the path to the \"Leistungsnachweise.xlsx\" as parameter.");
			System.exit(0);
		} else {
			path = args[0];
		}

		try {

			ArrayList<ArrayList<Object>> sheetList = new ReadExcel().readTheXLSXFile(path);
			ArrayList<CallHolder> callholders = new XLSXParser(sheetList).convertValues();

			// new CSVWriter().createCSV(callholders);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
