package com.timbryant.excel.poi;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * POI解析Excel文件
 * 
 * @author liuxf
 * 
 */
public class PoiReadExcel {
	public static void main(String[] args) {
		// 需要解析的文件
		File file = new File("E:/poi_excel.xls");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
			// 根据名字获得工作标签页
			// HSSFSheet sheet=workbook.getSheet("Sheet0");
			// 根据位置读取标签页
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNumber = 0;
			int lastRowNumber = sheet.getLastRowNum();// 获取最后一行
			for (int i = firstRowNumber; i < lastRowNumber; i++) {
				HSSFRow row = sheet.getRow(i);
				// 获得当前行最后一个单元格号
				int firstCellNumber = row.getFirstCellNum();
				int lastCellNumber = row.getLastCellNum();
				for (int j = firstCellNumber; j < lastCellNumber; j++) {
					HSSFCell cell = row.getCell(j);
					String value = cell.getStringCellValue();
					System.out.print(value+" ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
