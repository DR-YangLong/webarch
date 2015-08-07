package com.webarch.common.io.excel.poi;

import com.webarch.common.io.excel.poi.convert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构建对象的类，目前只支持普通类型属性，<br/>
 * 可以在一开始指定要构建目标对象的哪些属性，默认取其所有普通值类型和String、Date属性
 * 
 * @author Asgic
 *
 */
public class ObjectBuilder {
	private Logger log = LoggerFactory.getLogger(ObjectBuilder.class);
	private Object obj;
	private Map<String,AssignmentInvoke> setters;
	/**
	 * 记录警告日志
	 * 
	 * @param e
	 */
	final void logWarn(Exception e) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		log.warn(e.getMessage() + ste.getClassName() + ste.getMethodName()
				+ "[" + ste.getLineNumber() + "]");
	}

	/**
	 * 通过类名和属性创建一个对象构造器，在使用时只会对已指定的属性进行赋值操作
	 * 
	 * @param name
	 *            类名
	 * @param fields
	 *            属性的集合
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public ObjectBuilder(String name, List<Field> fields)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, NoSuchMethodException, SecurityException {
		this.obj = Class.forName(name).newInstance();
		this.initSetters(fields);
	}

	/**
	 * 根据setter方法创建一个属性赋值器，其中主要是创建各个数据转换器
	 *
	 * @param field
	 *            属性对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private AssignmentInvoke buildAssignmentInvoke(Field field) {
		Method m=field.getMethod();
		Class<?>[] ts = m.getParameterTypes();
		if (ts.length == 1) {
			Class<?> t = ts[0];
			if (t.getName().startsWith("java.") || !t.getName().contains(".")) {
				IValueConvert valueConvert = null;
				switch (t.getSimpleName().toLowerCase()) {
				case "boolean":
					valueConvert = new BooleanConvert();
					break;
				case "byte":
					valueConvert = new ByteConvert();
					break;
				case "short":
					valueConvert = new ShortConvert();
					break;
				case "char":
				case "character":
					valueConvert = new CharacterConvert();
					break;
				case "int":
				case "integer":
					valueConvert = new IntegerConvert();
					break;
				case "long":
					valueConvert = new LongConvert();
					break;
				case "float":
					valueConvert = new FloatConvert();
					break;
				case "double":
					valueConvert = new DoubleConvert();
					break;
				case "string":
					valueConvert = new StringConvert();
					break;
				case "date":
					valueConvert = new DateConvert(field.getMark().get(
							"dateType"));
					break;
				}
				return valueConvert == null ? null : new AssignmentInvoke(m, t,
						valueConvert);// 如果不是普通值对象和String、Date对象则不进行处理
			}
		}
		return null;
	}

	/**
	 * 根据指定的字段进行初始化setter方法
	 * 
	 * @param fields
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void initSetters(Collection<Field> fields)
			throws NoSuchMethodException, SecurityException {
		this.setters = new HashMap<String, AssignmentInvoke>(fields.size());
		for(Field field:fields){
			this.setters.put(field.getName(), this.buildAssignmentInvoke(field));
		}
	}

	/**
	 * 设置一个属性的值
	 * @param fieldName 目标属性名，去掉set前缀的首字母小写
	 * @param value 目标属性值
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void setField(String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		this.setters.get(fieldName).invoke(this.obj, value);
	}

	/**
	 * 获取一个认为已经构建完成的对象，如果想进行下一个当前对象类型的对象构建，请执行newObject()方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject() {
		return (T) this.obj;
	}

	/**
	 * 新建一个对象，如果想获取已经构建完成的对象，请使用getObject
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void newObject() throws InstantiationException,
			IllegalAccessException {
		this.obj = this.obj.getClass().newInstance();
	}
}
