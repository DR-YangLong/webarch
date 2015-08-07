package com.webarch.common.io.excel.poi.convert;


/**
 * string类型转换器，为了统一，所以需要建立一个String转换器
 * 
 * @author Asgic
 *
 */
public class StringConvert implements IValueConvert<String> {
	@Override
	public String parse(String obj) {
		return obj;
	}
}