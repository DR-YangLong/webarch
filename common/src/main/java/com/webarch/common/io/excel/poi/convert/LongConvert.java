package com.webarch.common.io.excel.poi.convert;


/**
 * long类型转换器
 * 
 * @author Asgic
 *
 */
public class LongConvert implements IValueConvert<Long> {
	@Override
	public Long parse(String obj) {
		if (obj.contains(".")) {
			obj = obj.substring(0, obj.indexOf("."));
		}
		return Long.parseLong(obj);
	}
}