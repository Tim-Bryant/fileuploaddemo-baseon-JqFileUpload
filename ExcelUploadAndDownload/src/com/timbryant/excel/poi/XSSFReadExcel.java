package com.timbryant.excel.poi;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * POI高级的XSSF解析Excel（07版本以上xlsx）文件
 * 
 * @author liuxf
 * 
 */
public class XSSFReadExcel {
	public static void main(String[] args) {
		// 需要解析的文件
		File file = new File("E:/poi_excel.xlsx");
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(FileUtils.openInputStream(file));
			// 根据名字获得工作标签页
			// Sheet sheet=workbook.getSheet("Sheet0");
			// 根据位置读取标签页
			Sheet sheet = workbook.getSheetAt(0);
			int firstRowNumber = 0;
			int lastRowNumber = sheet.getLastRowNum();// 获取最后一行
			for (int i = firstRowNumber; i < lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				// 获得当前行最后一个单元格号
				int firstCellNumber = row.getFirstCellNum();
				int lastCellNumber = row.getLastCellNum();
				for (int j = firstCellNumber; j < lastCellNumber; j++) {
					Cell cell = row.getCell(j);
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
