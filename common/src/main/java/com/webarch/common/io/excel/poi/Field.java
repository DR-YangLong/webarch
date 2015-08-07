package com.webarch.common.io.excel.poi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Field implements Comparable<Field> {
	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 其他标记
	 */
	private Map<String, String> mark;
	/**
	 * Excel的标题
	 */
	private String title;
	/**
	 * 对应对象的方法，setter|getter方法
	 */
	private Method method;
	private DateFormat dateFormat;
	/**
	 * 在Excel文件中的顺序
	 */
	private int index;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getMark() {
		return mark;
	}

	public void setMark(Map<String, String> mark) {
		this.mark = mark;
	}

	/**
	 * 取得格式化后的日期字符串
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public String getDateStringValue(Object obj) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		if (this.dateFormat == null && mark.get("dateType") != null) {
			this.dateFormat = new SimpleDateFormat(mark.get("dateType"));
		}
		if (this.dateFormat == null) {
			return null;
		}
		Object value = this.invokeGetter(obj);
		if (value != null && Date.class.isAssignableFrom(value.getClass())) {// 要格式化的日期不能为空且对象为Date
			return this.dateFormat.format((Date) value);
		}
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length == 0 && method.getReturnType() != null
				&& !method.getReturnType().equals(Void.class)) {
			this.type = method.getReturnType().getSimpleName().toLowerCase();
		} else if (paramTypes.length == 1) {
			this.type = paramTypes[0].getSimpleName().toLowerCase();
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int compareTo(Field o) {
		return this.index - o.index;
	}

	/**
	 * 执行此Field对应的方法
	 * 
	 * @param obj
	 *            要执行的对象
	 * @param parameters
	 *            执行的参数
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object invoke(Object obj, Object[] parameters)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return this.method.invoke(obj, parameters);
	}

	/**
	 * 把当前Field作为setter方法执行
	 * 
	 * @param obj
	 *            要执行的对象
	 * @param parameter
	 *            要传递的参数
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void invokeSetter(Object obj, Object parameter)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Object[] ps = new Object[] { parameter };
		this.invoke(obj, ps);
	}

	/**
	 * 把当前Field作为getter方法执行
	 * 
	 * @param obj
	 *            要执行的对象
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object invokeGetter(Object obj) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Object[] ps = new Object[] {};
		return this.invoke(obj, ps);
	}

	public static void main(String[] args) {
		int a = 0;
		Object o = a;
		System.out.println(o.toString());
	}
}
