package com.webarch.common.io.excel.poi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel对象读取器的配置类，一般是指一个properties文件
 * 
 * @author Asgic
 *
 */
public class POIConfigure {
	private Logger log = LoggerFactory.getLogger(POIConfigure.class);
	private Properties properties;
	private static final Class<?>[] EMPTY_TYPE_ARRAY = new Class<?>[] {};

	/**
	 * 通过路径创建一个配置
	 * 
	 * @param path
	 * @throws IOException
	 */
	public POIConfigure(String path) throws IOException {
		InputStream inputStream = null;
		if (path.startsWith("classpath:")) {
			inputStream = POIConfigure.class.getClassLoader()
					.getResource(path.replace("classpath:", "")).openStream();
		} else {
			inputStream = new FileInputStream(path);
		}
		try {
			this.initFromInputStream(inputStream);
		} catch (IOException e1) {
			logWarn(e1);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				logWarn(e);
			}
		}
	}

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
	 * 通过URL创建一个配置
	 * 
	 * @param url
	 * @throws IOException
	 */
	public POIConfigure(URL url) throws IOException {
		InputStream inputStream = url.openStream();
		this.initFromInputStream(inputStream);
		try {
			inputStream.close();
		} catch (IOException e) {
			logWarn(e);
		} finally {
			inputStream.close();
		}
	}

	/**
	 * 通过一个输入流创建一个配置
	 * 
	 * @param inputStream
	 * @throws IOException
	 */
	public POIConfigure(InputStream inputStream) throws IOException {
		this.initFromInputStream(inputStream);
	}

	/**
	 * 从流中加载配置信息
	 * 
	 * @param inputStream
	 * @throws IOException
	 */
	private void initFromInputStream(InputStream inputStream)
			throws IOException {
		this.properties = new Properties();
		this.properties.load(inputStream);
	}

	/**
	 * 获取目标类型的类名
	 * 
	 * @return
	 */
	public String getClassName() {
		return this.properties.getProperty("class");
	}

	/**
	 * 读取文件时文件内是否有标题
	 * 
	 * @return
	 */
	public boolean hasTitle() {
		return Boolean.parseBoolean(this.properties.getProperty("hasTitle"));
	}

	/**
	 * 设置读取方向(可能是以行为一个单位或者是以列为一个单位)，目前未实现
	 * 
	 * @return
	 */
	public String getReadDirection() {
		return this.properties.getProperty("readDirection");
	}

	/**
	 * 获取配置文件中所配置的字段对应信息<br/>
	 * 配置文件中的属性名是以<strong>field[<i style="color:#00f">列索引</i>|<i
	 * style="color:#00f">行索引</i>]</strong>定义一个字段值所在Excel文件位置的，<br/>
	 * 属性后面可以跟各种标志，比如日期格式:<br/>
	 * field[1].dateType=yyyy-MM-dd //是指第二列这个数据为Date类型，且日期格式为yyyy-MM-dd
	 * 
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	// public Map<Integer, Field> getSetFields() {
	// String regex = "field\\[(\\d)\\]";//
	// Pattern pattern = Pattern.compile("\\d+");
	// Set<Object> ks = this.properties.keySet();
	// Map<Integer, Field> fields = new HashMap<Integer, Field>();
	// for (Object k : ks) {
	// String name = k.toString();
	// Matcher matcher = pattern.matcher(name);
	// if (Pattern.matches(regex, name) && matcher.find()) {// 取得行号|列号
	// int index = Integer.parseInt(matcher.group());
	// Field field = new Field();
	// field.setName(this.properties.getProperty(name));// 取得属性的名字
	// field.setMark(new HashMap<String, String>());
	// field.setTitle(this.properties.getProperty(name + ".title"));
	// for (Object mk : ks) {// 属性后面可以跟各种标志，比如日期格式field[1].dateType=yyyy-MM-dd
	// String markName = mk.toString();
	// if (markName.startsWith(name + ".")) {
	// field.getMark().put(markName.replace(name + ".", ""),
	// this.properties.getProperty(markName));
	// }
	// }
	// fields.put(index, field);
	// }
	// }
	// return fields;
	// }

	public List<Field> getSetFields() throws ClassNotFoundException,
			NoSuchMethodException, SecurityException {
		return this.getFields("set");
	}

	/**
	 * 获取所有对应的getter方法
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public List<Field> getGetFields() throws ClassNotFoundException,
			NoSuchMethodException, SecurityException {
		return this.getFields("get");
	}

	private List<Field> getFields(String prefix) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException {
		List<Field> fields = new ArrayList<Field>(5);
		String regex = "field\\[(\\d)\\]";//
		Pattern pattern = Pattern.compile("\\d+");
		Set<Object> ks = this.properties.keySet();
		Class<?> classObj = Class.forName(this.getClassName());
		for (Object k : ks) {
			String name = k.toString();
			Matcher matcher = pattern.matcher(name);
			if (Pattern.matches(regex, name) && matcher.find()) {// 取得行号|列号
				int index = Integer.parseInt(matcher.group());
				Field field = new Field();
				field.setName(this.properties.getProperty(name));// 取得属性的名字
				field.setMark(new HashMap<String, String>());
				field.setMethod(getMethod(prefix, field.getName(), classObj));
				field.setTitle(this.properties.getProperty(name + ".title"));
				field.setIndex(index);
				field.setType(this.properties
						.getProperty(name + ".outDataType"));
				this.setMark(name, field.getMark());
				fields.add(field);
			}
		}
		Collections.sort(fields);
		return fields;
	}

	private void setMark(String name, Map<String, String> markMap) {
		Set<Object> ks = this.properties.keySet();
		name += ".";
		for (Object k : ks) {
			String key = k.toString();
			if (key.startsWith(name)) {
				key = key.substring(name.length());
				if (!"title".equals(key) && !"outDataType".equals(key)) {
					markMap.put(key, this.properties.getProperty(k.toString()));
				}
			}
		}
	}

	private Method getMethod(String prefix, String field, Class<?> cla)
			throws NoSuchMethodException, SecurityException {
		field = field.substring(0, 1).toUpperCase() + field.substring(1);
		Method get = cla.getMethod("get" + field, EMPTY_TYPE_ARRAY);
		Class<?> returnType = get.getReturnType();
		Method set = cla.getMethod("set" + field, returnType);
		return "get".equals(prefix) ? get : "set".equals(prefix) ? set : null;
	}
}