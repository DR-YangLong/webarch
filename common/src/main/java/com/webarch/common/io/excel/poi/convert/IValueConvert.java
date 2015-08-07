package com.webarch.common.io.excel.poi.convert;

/**
 * 值转换器，一般是指将字符串转换为目标属性所需要的值
 * 
 * @author Asgic
 *
 * @param <T>
 */
public interface IValueConvert<T> {
	/**
	 * 转换一个String对象为目标需要的值
	 * 
	 * @param obj
	 * @return
	 */
	T parse(String obj);
}
