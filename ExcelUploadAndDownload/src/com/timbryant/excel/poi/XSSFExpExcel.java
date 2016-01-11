package com.timbryant.excel.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 针对Excel-2007以上版本的解决方案
 * 
 * @author liuxf
 * 
 */
public class XSSFExpExcel {
	public static void main(String[] args) {
		String[] title = new String[] { "id", "name", "sex" };
		// 创建Excel工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个工作表sheet
		Sheet sheet = workbook.createSheet();
		// 创建第一行
		Row row = sheet.createRow(0);
		Cell cell = null;
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据
		for (int i = 1; i < 10; i++) {
			Row nextrow = sheet.createRow(i);
			Cell cell1 = nextrow.createCell(0);
			cell1.setCellValue("a" + 1);
			Cell cell2 = nextrow.createCell(1);
			cell2.setCellValue("user" + 1);
			Cell cell3 = nextrow.createCell(2);
			cell3.setCellValue("男");
		}
		// 创建空文件
		File file = new File("E:/poi_excel.xlsx");
		try {
			file.createNewFile();
			// 将Excel的流写入到文件流
			FileOutputStream stream = FileUtils.openOutputStream(file);
			workbook.write(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
