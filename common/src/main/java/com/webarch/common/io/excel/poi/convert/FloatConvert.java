package com.webarch.common.io.excel.poi.convert;


/**
 * float类型转换器
 * 
 * @author Asgic
 *
 */
public class FloatConvert implements IValueConvert<Float> {
	@Override
	public Float parse(String obj) {
		return Float.parseFloat(obj);
	}
}
