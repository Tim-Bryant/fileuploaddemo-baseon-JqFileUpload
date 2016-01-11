package com.timbryant.excel.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * POI导出Excel
 * 
 * @author liuxf
 * 
 */
public class PoiExpExcel {
	public static void main(String[] args) {
		String[] title = new String[] { "id", "name", "sex" };
		// 创建Excel工作薄
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表sheet
		HSSFSheet sheet = workbook.createSheet();
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i = 0; i < title.length; i++) {
		    cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据
		for (int i = 1; i < 10; i++) {
			HSSFRow nextrow = sheet.createRow(i);
			HSSFCell cell1 = nextrow.createCell(0);
			cell1.setCellValue("a" + 1);
			HSSFCell cell2 = nextrow.createCell(1);
			cell2.setCellValue("user" + 1);
			HSSFCell cell3 = nextrow.createCell(2);
			cell3.setCellValue("男");
		}
		// 创建空文件
		File file = new File("E:/poi_excel.xls");
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
