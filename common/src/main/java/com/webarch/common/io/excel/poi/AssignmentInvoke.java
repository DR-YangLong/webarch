package com.webarch.common.io.excel.poi;

import com.webarch.common.io.excel.poi.convert.IValueConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AssignmentInvoke {
	private Logger log = LoggerFactory.getLogger(AssignmentInvoke.class);
	/**
	 * 当前属性对应的setter方法
	 */
	private Method method;
	/**
	 * 用于当前属性值转换的对象
	 */
	@SuppressWarnings({ "rawtypes" })
	private IValueConvert valueConvert;
	/**
	 * 当前属性的类型
	 */
	private Class<?> parameterType;

	@SuppressWarnings("rawtypes")
	public AssignmentInvoke(Method method, Class<?> parameterType,
			IValueConvert valueConvert) {
		this.method = method;
		this.parameterType = parameterType;
		this.valueConvert = valueConvert;
	}

	public Object invoke(Object obj, Object value) {
		try {
			if(value==null){//如果参数为空，则为执行getter方法
				return this.method.invoke(obj, new Object[]{});
			}
			if (parameterType.isAssignableFrom(value.getClass())) {// 如果给的值是当前属性所对应/期待的值类型，则直接给予赋值
				this.method.invoke(obj, value);
			} else {// 否则就需要调用值转换器来进行转换了，一般是通过将给定的值转换为String，然后再转换为目标值
				this.method.invoke(obj,
						this.valueConvert.parse(value.toString()));
			}
		} catch (IllegalAccessException e) {
			logWarn(e);
		} catch (IllegalArgumentException e) {
			logWarn(e);
		} catch (InvocationTargetException e) {
			logWarn(e);
		}
		return null;
	}/**
	 * 记录警告日志
	 * 
	 * @param e
	 */
	final void logWarn(Exception e) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		log.warn(e.getMessage() + ste.getClassName() + ste.getMethodName()
				+ "[" + ste.getLineNumber() + "]");
	}
}
