package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static Map<String, List<Map<String, String>>> sheetData = new HashMap<>();

	public static List<Map<String, String>> readDataFromExcel(String sheetName) {

		if (sheetData.containsKey(sheetName)) {
			return sheetData.get(sheetName);
		}

		List<Map<String, String>> dataList = new ArrayList<>();
		Properties prop = ConfigReader.initializeProperties();

		try (FileInputStream fis = new FileInputStream(prop.getProperty("xlPath"));
				Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet not found: " + sheetName);
			}

			Row headerRow = sheet.getRow(0);
			int colCount = headerRow.getLastCellNum();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Map<String, String> rowData = new HashMap<>();

				for (int j = 0; j < colCount; j++) {
					Cell headerCell = headerRow.getCell(j);
					Cell cell = row.getCell(j);

					String header = headerCell.getStringCellValue().trim();
					String value = "";

					if (cell != null) {
						switch (cell.getCellType()) {
						case STRING -> value = cell.getStringCellValue().trim();
						case NUMERIC -> value = DateUtil.isCellDateFormatted(cell)
								? new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue())
								: String.valueOf((int) cell.getNumericCellValue());
						case BOOLEAN -> value = String.valueOf(cell.getBooleanCellValue());
						case FORMULA -> value = cell.getCellFormula();
						default -> value = "";
						}
					}

					rowData.put(header, value);
				}

				dataList.add(rowData);
			}

		} catch (IOException e) {
			throw new RuntimeException("Error reading Excel file: " + prop.getProperty("xlPath"), e);
		}

		sheetData.put(sheetName, dataList);

		return dataList;
	}

	public static Map<String, String> getTestData(String scenarioType) {

		for (String sheetName : sheetData.keySet()) {
			List<Map<String, String>> rows = sheetData.get(sheetName);

			for (Map<String, String> row : rows) {
				String cellValue = row.get("scenario_type");
				if (cellValue != null && cellValue.equalsIgnoreCase(scenarioType)) {
					return row;
				}
			}
		}

		throw new IllegalArgumentException("No row found with scenario_type = " + scenarioType);
	}

}
