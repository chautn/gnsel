package gnsel.testdata.excel;

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

public class OvalTable {
	String file;
	String sheetName;
	String key;
	Sheet sheet;
	
	public OvalTable(String file, String sheetName, String key) {
		this.file = file;
		this.sheetName = sheetName;
		this.key = key;
		try {
			File file_ = new File(ClassLoader.class.getResource(file).toURI());
			Workbook wb = WorkbookFactory.create(file_);
			sheet = wb.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<HashMap<String, String>> table() {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		Cell top = null;
		Cell bot = null;
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cellString(cell).equals(key)) {
					if (top == null) {
						top = cell;
					} else if (bot == null) {
						bot = cell;
						break;
					}
				}
			}
		}
		int topX = top.getColumnIndex();
		int topY = top.getRowIndex();
		int botX = bot.getColumnIndex();
		int botY = bot.getRowIndex();
		for (int y = topY + 2; y < botY; y++) {
			HashMap<String, String> map = new HashMap<String, String>();
			for (int x = topX + 1; x < botX; x++) {
				map.put(cellString(sheet.getRow(topY + 1).getCell(x)), cellString(sheet.getRow(y).getCell(x)));
			}
			result.add(map);
		}
		return result;
	}
	private String cellString(Cell cell) {
		DataFormatter formatter = new DataFormatter();
		FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
		evaluator.evaluate(cell);
		return formatter.formatCellValue(cell, evaluator);
	}
}
