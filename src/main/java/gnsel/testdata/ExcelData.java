package gnsel.testdata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

public class ExcelData {
	
	/**
	 * Returns the whole table data as List of HashMap. The first row is used as key.
	 * The first row must NOT be EMPTY.
	 * If a cell of first row is EMPTY, its column will be ignored.
	 * If a row is EMPTY, it will be ignored.
	 */
	public static List<HashMap<String, String>> table(String file, String sheetName) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			File file_ = new File(ClassLoader.class.getResource(file).toURI());
			Workbook wb = WorkbookFactory.create(file_);
			Sheet sheet = wb.getSheet(sheetName);
			Row head = sheet.getRow(0);
			for (Row row : sheet) {
				if (row.getRowNum() > 0) {
					HashMap<String, String> map = new HashMap<String, String>();
					for (Cell head_cell : head) {
						int j = head_cell.getColumnIndex();
						map.put(getCellValueAsString(wb, head_cell), getCellValueAsString(wb, row.getCell(j)));
					}
					result.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	private static String getCellValueAsString(Workbook wb, Cell cell) {
		DataFormatter formatter = new DataFormatter();
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluate(cell);
		return formatter.formatCellValue(cell, evaluator);
	}
	
	@Test
	public void test() {
		List<HashMap<String, String>> list = table("/data/sample/testExcelDataClass.xlsx", "Main");
		int y = list.size();
		for (int i = 0; i < y; i++) {
			HashMap<String, String> map = list.get(i);
			String s = "";
			for (String val : map.values()) {
				s = s + "[" + val + "]";
			}
			System.out.println(s);
		}
	}
}
