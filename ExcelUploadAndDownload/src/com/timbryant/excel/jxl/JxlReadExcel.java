package com.timbryant.excel.jxl;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * JXL解析excel文件
 * 
 * @author liuxf
 * 
 */
public class JxlReadExcel {
	public static void main(String[] args) {
		try {
			//创建工作表
			Workbook workbook = Workbook.getWorkbook(new File("E:/jxl_excel.xls"));
			//获取sheet页
			Sheet sheet = workbook.getSheet(0);
			for(int i=0;i<sheet.getRows();i++){
				for(int j=0;j<sheet.getColumns();j++){
					Cell cell=sheet.getCell(j, i);
					System.out.print(cell.getContents()+" ");
				}
				System.out.println();
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
