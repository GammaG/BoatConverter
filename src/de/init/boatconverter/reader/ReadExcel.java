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

public class ReadExcel {

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
					// skip the first 9 useless values
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
			}
			sheetList.add(valueList);
		}
		file.close();
		workbook.close();
		return sheetList;
	}

}
