package de.init.boatconverter.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.init.boatconverter.converter.Constants;
import de.init.boatconverter.pojos.CallHolder;

public class ExcelReader {

	private int PERSON = -1;
	private int PRICECLASS = -1;
	private int WORKDESCRIPTION = -1;
	private int DATE = -1;
	private int EFFORT = -1;
	private int TIMEFROM = -1;
	private int TIMETO = -1;

	public ArrayList<CallHolder> readTheXLSXFile(String path) throws IOException {
		ArrayList<CallHolder> callholders = new ArrayList<>();
		// FileInputStream file = new FileInputStream(new
		// File("files\\Leistungsnachweise.xlsx"));
		FileInputStream file = new FileInputStream(new File(path));

		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			ArrayList<Object> valueList = new ArrayList<>();
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(i);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				CallHolder callHolder = new CallHolder();
				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (row.getRowNum() == 0) {
						matchElement(cell);
						continue;
					} else {
						try {
							callHolder = parseCellValues(cell, callHolder);
						} catch (IllegalStateException e) {
							System.out.println(e);
						}
					}
				}
				if (row.getRowNum() == 0) {
					checkRows();
					continue;
				}
				callholders.add(callHolder);
			}
		}
		file.close();
		workbook.close();
		return callholders;
	}

	private void matchElement(Cell cell) {

		String s = cell.getStringCellValue();
		switch (s) {
		case "Mitarbeiter":
			PERSON = cell.getColumnIndex();
			break;
		case "Aufgabe":
			PRICECLASS = cell.getColumnIndex();
			break;
		case "Beschreibung":
			WORKDESCRIPTION = cell.getColumnIndex();
			break;
		case "Datum":
			DATE = cell.getColumnIndex();
			break;
		case "Dauer":
			EFFORT = cell.getColumnIndex();
			break;
		case "Startzeit":
			TIMEFROM = cell.getColumnIndex();
			break;
		case "Ende":
			TIMETO = cell.getColumnIndex();
			break;

		}
	}

	private void checkRows() {
		if (PERSON == -1 | PRICECLASS == -1 | WORKDESCRIPTION == -1 | DATE == -1 | EFFORT == -1 | TIMEFROM == -1 | TIMETO == -1) {
			Constants.dialog("Not all needed field have been given.\nNeeded are:\nMitarbeiter\nAufgabe\nBeschreibung\nDatum\nDauer\nStartzeit\nEnde\n");
			System.exit(1);
		}
	}

	private CallHolder parseCellValues(Cell cell, CallHolder callHolder) {
		int i = cell.getColumnIndex();
		if (i == PERSON)
			callHolder.setPerson(cell.getStringCellValue());
		else if (i == PRICECLASS)
			callHolder.setPriceclass(Constants.parsePriceLevel(cell.getStringCellValue()));
		else if (i == WORKDESCRIPTION)
			callHolder.setWorkDescription(cell.getStringCellValue());
		else if (i == DATE)
			callHolder.setDate("" + cell.getNumericCellValue());
		else if (i == EFFORT)
			callHolder.setTimeEffort(cell.getNumericCellValue());
		else if (i == TIMEFROM)
			callHolder.setTimeFrom(cell.getNumericCellValue());
		else if (i == TIMETO)
			callHolder.setTimeTo(cell.getNumericCellValue());

		return callHolder;
	}

}
