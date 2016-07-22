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

public class ExcelReader {

	private int PERSON = -1;
	private int PRICECLASS = -1;
	private int WORKDESCRIPTION = -1;
	private int DATE = -1;
	private int EFFORT = -1;
	private int TIMEFROM = -1;
	private int TIMETO = -1;

	public ArrayList<ArrayList<Object>> readTheXLSXFile(String path) throws IOException {
		ArrayList<ArrayList<Object>> sheetList = new ArrayList<>();
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
				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (row.getRowNum() == 0) {
						matchElement(cell);
						continue;
					}
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						valueList.add(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						valueList.add(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						valueList.add(null);
						break;
					}
				}
				if (row.getRowNum() == 0) {
					checkRows();
				}
			}
			sheetList.add(valueList);
		}
		file.close();
		workbook.close();
		return sheetList;
	}

	private void matchElement(Cell cell) {
		String s = cell.getStringCellValue();
		switch (s) {
		case "Mitarbeiter":
			PERSON = cell.getColumnIndex();
			break;
		case "Pfad":
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
			Constants.dialog("Not all needed field have been given.\nNeeded are:\nMitarbeiter\nPfad\nBeschreibung\nDatum\nDauer\nStartzeit\nEnde\n");
			System.exit(1);
		}

	}

}
