package utils;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTestDataManager {

	private final String filePath;

	public ExcelTestDataManager(String relativePath) {
		this.filePath = Paths.get(System.getProperty("user.dir"),"src","test","resources",relativePath).toString();
	}

	/**
	 * Reads any worksheet and returns data as List<Map<String, Object>>.
	 * Each row becomes a Map where keys are column headers.
	 */
	public List<Map<String, Object>> getSheetData(String sheetName) throws Exception {
		List<Map<String, Object>> sheetData = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
			Sheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rows = sheet.iterator();

			// Read headers (first row)
			Row headerRow = rows.next();
			List<String> headers = new ArrayList<>();
			for (Cell cell : headerRow) {
				headers.add(cell.getStringCellValue());
			}

			// Read data rows
			while (rows.hasNext()) {
				Row row = rows.next();
				Map<String, Object> rowData = new HashMap<>();
				for (int i = 0; i < headers.size(); i++) {
					Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					rowData.put(headers.get(i), getCellValue(cell));
				}
				sheetData.add(rowData);
			}
		}
		return sheetData;
	}

	// Helper: Convert Excel cell to Java type
	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return (int) cell.getNumericCellValue(); // Cast to int for IDs
		case BOOLEAN:
			return cell.getBooleanCellValue();
		default:
			return null;
		}
	}
}
