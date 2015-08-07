package com.webarch.common.io.excel.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel对象读取
 * 
 * @author Asgic
 *
 */
public class POIExcelObjectReader {
	/**
	 * 对象构建器
	 */
	private ObjectBuilder objectBuilder;
	/**
	 * 字段列表
	 */
	private List<Field> fields;
	/**
	 * 对于大数字进行格式化的类，因为一般的数字（Double在Excel中数字的类型）都是科学计数法
	 */
	private NumberFormat numberFormat;
	private boolean hasTitle;

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
	public POIExcelObjectReader(POIConfigure configure)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, NoSuchMethodException, SecurityException {
		this.objectBuilder = new ObjectBuilder(configure.getClassName(),
				configure.getSetFields());
		this.numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
		this.fields = configure.getSetFields();
		this.hasTitle = configure.hasTitle();
	}

	/**
	 * 从一个流中读取对象
	 * 
	 * @param inp
	 *            输入流
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> List<T> read(InputStream inp) throws InvalidFormatException,
			IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<T> result = new ArrayList<T>();
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);// 取得默认的第一页
		int rowCount = sheet.getLastRowNum();// 取得总行数，其实这个是忽悠人的，行数没有，只有最后一行
		int rowNum = this.hasTitle ? 1 : 0;
		for (; rowNum <= rowCount; rowNum++) {// 遍历所有的行，并构建对象
			Row r = sheet.getRow(rowNum);
			if (r == null) {
				continue;
			}
			int cellCount = r.getLastCellNum();
			for (int j = 0; j < cellCount; j++) {
				Cell c = r.getCell(j);
				if (c == null) {
					continue;
				}
				Object value;
				switch (c.getCellType()) {
				case Cell.CELL_TYPE_BLANK:// 空白值直接略过
					continue;
				case Cell.CELL_TYPE_BOOLEAN:// boolean值
					value = c.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_ERROR:// 错误直接略过
					continue;
				case Cell.CELL_TYPE_FORMULA:// 公式值
					value = c.getCellFormula();
					break;
				case Cell.CELL_TYPE_NUMERIC:// 数字值，包括日期时间
					double tmp = c.getNumericCellValue();
					short format = c.getCellStyle().getDataFormat();
					if (format == 14 || format == 31 || format == 57
							|| format == 58 || format == 20 || format == 32) {// Excel中日期的格式标识
						value = DateUtil.getJavaDate(tmp);
					} else {// 普通数值数据
						value = numberFormat.format(tmp);
					}
					break;
				case Cell.CELL_TYPE_STRING:// 普通字符串值
					value = c.getStringCellValue().trim();
					break;
				default:
					continue;
				}
				this.objectBuilder
						.setField(this.fields.get(j).getName(), value);// 确定属性，并赋值
			}
			T obj = this.objectBuilder.getObject();
			result.add(obj);
			this.objectBuilder.newObject();
		}
		return result;
	}
}
