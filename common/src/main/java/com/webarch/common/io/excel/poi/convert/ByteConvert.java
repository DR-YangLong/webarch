package com.webarch.common.io.excel.poi.convert;


/**
 * byte类型转换器
 * 
 * @author Asgic
 *
 */
public class ByteConvert implements IValueConvert<Byte> {
	@Override
	public Byte parse(String obj) {
		if (obj.contains(".")) {
			obj = obj.substring(0, obj.indexOf("."));
		}
		return Byte.parseByte(obj);
	}
}