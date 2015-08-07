package com.webarch.common.io.excel.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.List;

public class POIExcelObjectWriter {
	private List<Field> fields;

	/**
	 * 根据一个配置信息构建一个对象读取器
	 * 
	 * @param configure
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public POIExcelObjectWriter(POIConfigure configure) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, NoSuchMethodException, SecurityException {
		this.fields = configure.getGetFields();
	}

	public void write(OutputStream os, List<? extends Object> objs, String title) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < this.fields.size(); i++) {// 创建标题
			Field field = this.fields.get(i);
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(field.getTitle());
		}
		for (int i = 0; i < objs.size(); i++) {
			Object obj = objs.get(i);
			row = sheet.createRow(i + 1);// 创建行
			for (int j = 0; j < this.fields.size(); j++) {
				Field field = this.fields.get(j);// 取得当前单元格对应的字段
				HSSFCell cell = row.createCell(j);// 创建单元格
				Object value = field.invokeGetter(obj);// 执行getter方法，取得字段值
				if (value != null) {
					switch (field.getType()) {
						case "int":
						case "integer":
						case "short":
						case "byte":
							cell.setCellValue(Integer.parseInt(value.toString()));
							break;
						case "float":
						case "double":
							cell.setCellValue(Double.parseDouble(value.toString()));
							break;
						case "date":
							String vtmp = field.getDateStringValue(obj);
							if (vtmp == null && value != null && Date.class.isAssignableFrom(value.getClass())) {// 如果没有设置格式化格式，则直接设置数据为Date
								cell.setCellValue((Date) value);
							} else if (vtmp != null) {// 如果设置了格式化格式，则设置数据为格式化后的字符串
								cell.setCellValue(vtmp);
							} else if (value != null) {// 否则在值不为空的情况下，则设置值为默认的字符串格式
								cell.setCellValue(value.toString());
							}
							break;
						case "char":
						case "character":
						case "string":
						default:
							cell.setCellValue(value.toString());
							break;
					}
				}
			}
		}
		workbook.write(os);
	}
}