package com.webarch.common.io.excel.poi.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date类型转换器，需指定其格式
 * 
 * @author Asgic
 *
 */
public class DateConvert implements IValueConvert<Date> {
	private SimpleDateFormat dateFormat;

	public DateConvert(String pattern) {
		this.dateFormat = new SimpleDateFormat(pattern);
	}

	@Override
	public Date parse(String obj) {
		try {
			return this.dateFormat.parse(obj);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}