package com.prop.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class SimpleJsonUtil {

	/**
	 * 将map对象转换为json格式
	 * @return
	 */
	public static String map2json(Map<String, String> map) {
		if(map == null) return null;
		Set<Entry<String, String>> entry = map.entrySet();
		Iterator<Entry<String, String>> iter = entry.iterator();
		StringBuffer sb = new StringBuffer("{");
		while(iter.hasNext()) {
			Entry<String, String> e = iter.next();
			sb.append("\"" + e.getKey() + "\":\""+e.getValue()+"\",");
		}
		if(sb.length()>1) sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}
	
}
