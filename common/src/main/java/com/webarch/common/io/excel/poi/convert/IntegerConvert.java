package com.webarch.common.io.excel.poi.convert;


/**
 * int类型转换器
 * 
 * @author Asgic
 *
 */
public class IntegerConvert implements IValueConvert<Integer> {
	@Override
	public Integer parse(String obj) {
		if (obj.contains(".")) {
			obj = obj.substring(0, obj.indexOf("."));
		}
		return Integer.parseInt(obj);
	}
}