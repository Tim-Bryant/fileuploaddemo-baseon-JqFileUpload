package com.timbryant.excel.template;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * 创建模板文件
 * 
 * @author liuxf
 */
public class CreateTemplate {
	public static void main(String[] args) {
		// 获得文件路径 以下两种都可以
		//String path = System.getProperty("user.dir") + "/src/student.xml";
		String path = "./src/student.xml";
		System.out.println(path);
		File file = new File(path);
		// Jdom解析文件(文件中不要注释)
		SAXBuilder builder = new SAXBuilder();
		try {
			// 解析XML
			Document doc = builder.build(file);
			// 创建EXcel
			HSSFWorkbook book = new HSSFWorkbook();

			// 获取XML的根节点
			Element rootElement = doc.getRootElement();
			// 获取模板名称
			String templateName = rootElement.getAttribute("name").getValue();
			// 以模板名称创建Sheet
			HSSFSheet sheet = book.createSheet(templateName);

			// 设置开始的行，列计数
			int rownum = 0;
			int colnum = 0;

			// 设置列宽
			Element colgroup = rootElement.getChild("colgroup");
			setColumnWidth(sheet, colgroup);

			// 设置标题
			Element title = rootElement.getChild("title");
			List<Element> trs = title.getChildren("tr");
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				List<Element> tds = tr.getChildren("td");
				HSSFRow row = sheet.createRow(rownum);
				// 定义行样式
				HSSFCellStyle cellStyle = book.createCellStyle();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

				for (colnum = 0; colnum < tds.size(); colnum++) {
					Element td = tds.get(colnum);
					HSSFCell cell = row.createCell(colnum);
					Attribute rowspan = td.getAttribute("rowspan");
					Attribute colspan = td.getAttribute("colspan");
					Attribute value = td.getAttribute("value");
					if (value != null) {
						String val = value.getValue();
						cell.setCellValue(val);
						int rspan = rowspan.getIntValue() - 1;
						int cspan = colspan.getIntValue() - 1;
						// 设置字体
						HSSFFont font = book.createFont();
						font.setFontName("楷体");
						font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						// font.setFontHeight((short) 12);
						font.setFontHeightInPoints((short) 16);
						cellStyle.setFont(font);
						cell.setCellStyle(cellStyle);

						// 合并单元格居中
						sheet.addMergedRegion(new CellRangeAddress(rspan, rspan, 0, cspan));

					}
				}
				rownum++;
			}
			// 设置表头信息
			Element thead = rootElement.getChild("thead");
			trs = thead.getChildren("tr");
			// 定义行样式
			HSSFCellStyle cellStyle = book.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				HSSFRow row = sheet.createRow(rownum);
				List<Element> ths = tr.getChildren("th");
				for (colnum = 0; colnum < ths.size(); colnum++) {
					Element th = ths.get(colnum);
					Attribute valTh = th.getAttribute("value");
					HSSFCell cell = row.createCell(colnum);
					if (valTh != null) {
						String value = valTh.getValue();
						cell.setCellValue(value);
					}
					cell.setCellStyle(cellStyle);
				}
				rownum++;
			}

			// 设置数据区域的样式
			Element tbody = rootElement.getChild("tbody");
			// 获得第一个tr
			Element tr = tbody.getChild("tr");
			int repeat = tr.getAttribute("repeat").getIntValue();
			List<Element> tds = tr.getChildren("td");
			// 创建一个五行记录的模板内容
			for (int i = 0; i < repeat; i++) {
				HSSFRow row = sheet.createRow(rownum);
				// 每一行添加6列模板的单元格
				for (colnum = 0; colnum < tds.size(); colnum++) {
					Element td = tds.get(colnum);
					HSSFCell cell = row.createCell(colnum);
					setType(book, cell, td);

				}
				rownum++;
			}

			// 生成Excel到本地
			File tempFile = new File("e:/" + templateName + ".xls");
			tempFile.delete();
			tempFile.createNewFile();
			FileOutputStream stream = FileUtils.openOutputStream(tempFile);
			book.write(stream);
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置单元格样式
	 * 
	 * @param book
	 * @param cell
	 * @param td
	 */
	private static void setType(HSSFWorkbook book, HSSFCell cell, Element td) {
		Attribute typeAttr = td.getAttribute("type");
		String value = typeAttr.getValue();
		HSSFDataFormat format = book.createDataFormat();
		HSSFCellStyle cellStyle = book.createCellStyle();
		// 数字类型
		if ("NUMERIC".equalsIgnoreCase(value)) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			Attribute formatAttribute = td.getAttribute("format");
			String formatVal = formatAttribute.getValue();
			formatVal = StringUtils.isNotBlank(formatVal) ? formatVal : "#,##0.00";
			cellStyle.setDataFormat(format.getFormat(formatVal));

		} else if ("STRING".equalsIgnoreCase(value)) {// 字符串类型
			cell.setCellValue("");
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellStyle.setDataFormat(format.getFormat("@"));
		} else if ("DATE".equalsIgnoreCase(value)) {// 日期类型
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellStyle.setDataFormat(format.getFormat("yyyy-m-d"));
		} else if ("ENUM".equalsIgnoreCase(value)) {// 枚举类型
			CellRangeAddressList regionsAddressList = new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
			Attribute enumAttribute = td.getAttribute("format");
			String enumVal = enumAttribute.getValue();
			// 加载下拉列表内容
			DVConstraint constraint = DVConstraint.createExplicitListConstraint(enumVal.split(","));
			// 数据有效性对象
			HSSFDataValidation dataValidation = new HSSFDataValidation(regionsAddressList, constraint);
			book.getSheetAt(0).addValidationData(dataValidation);

		}
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 设置列宽
	 * 
	 * @param sheet
	 * @param colgroup
	 */
	private static void setColumnWidth(HSSFSheet sheet, Element colgroup) {
		List<Element> cols = colgroup.getChildren("col");
		for (int i = 0; i < cols.size(); i++) {
			Element col = cols.get(i);
			Attribute attrWidth = col.getAttribute("width");
			String unit = attrWidth.getValue().replaceAll("[0-9,\\.]", "");
			String value = attrWidth.getValue().replaceAll(unit, "");
			int v = 0;
			if (StringUtils.isBlank(unit) || "px".endsWith(unit)) {
				v = Math.round(Float.parseFloat(value) * 37F);// 固定公式
			} else if ("em".endsWith(unit)) {
				v = Math.round(Float.parseFloat(value) * 267.5F);
			}
			sheet.setColumnWidth(i, v);
		}
	}
}
