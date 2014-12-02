package com.prop.util;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

public class PropertiesUtil {

	private static Properties prop = new Properties();
	private static String realPath;
	
	public static void init(String properties) {
		if(realPath != null) throw new RuntimeException("Properties info has been inited!");
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			prop.load(loader.getResourceAsStream(properties));
			realPath = loader.getResource(properties).getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据key获取value
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * 根据key删除对应的键
	 * @param key
	 */
	public static void remove(String key) {
		prop.remove(key);
		store();
	}
	
	/**
	 * 更新properties文件。如果传入的key存在，则传入的value将覆盖原有的值key
	 * 如果传入的key不存在，则在properties文件中新增加相应的键值信息
	 * @param key
	 * @param val
	 */
	public static void set(String key,String val) {
		prop.setProperty(key, val);
		store();
	}
	
	/**
	 * 保存properties文件
	 */
	private static void store() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(realPath);
			prop.store(fos, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(fos != null) fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断properties文件中是否存在某个键
	 * @param key
	 * @return
	 */
	public static boolean hasKey(String key) {
		return prop.containsKey(key);
	}
	
	/**
	 * 判断properties文件中是否存在某个value
	 * @param value
	 * @return
	 */
	public static boolean hasValue(String value) {
		return prop.containsValue(value);
	}
	
	/**		
	 * 获取键值对信息
	 * @return
	 */
	public static Map<String, String> get() {
		Map<String, String> map = new HashMap<String, String>();
		Set<Entry<Object, Object>> entrys = prop.entrySet();
		for(Entry<Object, Object> en:entrys) {
			map.put((String)en.getKey(), (String)en.getValue());
		}
		return map;
	}
	
}
