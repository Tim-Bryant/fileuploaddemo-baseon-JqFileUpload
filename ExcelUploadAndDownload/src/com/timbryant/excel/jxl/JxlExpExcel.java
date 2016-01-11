package com.timbryant.excel.jxl;

import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * JXL创建Excel文件
 * 
 * @author liuxf
 * 
 */
public class JxlExpExcel {
	public static void main(String[] args) throws RowsExceededException, WriteException {
		
		String[] title=new String[]{"id","name","sex"};
		
		//创建Excel文件
        File file=new File("E:/jxl_excel.xls"); 
		try {
			file.createNewFile();
			//创建工作薄
			WritableWorkbook workbook=Workbook.createWorkbook(file);
			//创建Sheet
			WritableSheet sheet = workbook.createSheet("sheet1", 0);
			Label label=null;
			//第一行设置列名
			for(int i=0;i<title.length;i++){
				label=new Label(i, 0, title[i]);
				sheet.addCell(label);
			}
			//追加数据
			for(int i=1;i<10;i++){
				label=new Label(0, i, "a"+1);
				sheet.addCell(label);
				label=new Label(1, i, "user"+1);
				sheet.addCell(label);
				label=new Label(2, i, "女");
				sheet.addCell(label);
			}
			//写入数据
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
