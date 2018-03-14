package com.pomelo.file.FileCreate.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author zxz
 *
 */
public class StringBean {
	private String value;
	
	public StringBean() {
		
	}
	
	public StringBean(String value, int times) {
		if(times > 0) {
			StringBuffer sb = new StringBuffer(value);
			for (int i = 1; i < times; i++) {
				sb.append(value);
			}
			this.value = sb.toString();
		}
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String assign(String value) {
		this.value = value;
		return this.value;
	}
	
	public Map<String, String> split(String regex, LinkedHashSet<String> heads) {
		if(regex.equals("|")) {
			regex = "\\|";
		}
		String[] splits = value.split(regex);
		
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> head = heads.iterator();
		
		for(int i = 0;i < splits.length;i++) {
			map.put(head.next(), splits[i]);
		}
		
		return map;
	}
	
	private static char[] formatTypes = {'d', 's'};
	
	public static Object[] unformat(String value, String format) {
		int firstTypeIndex = format.indexOf("%");
		if(firstTypeIndex == -1) {
			return null;
		}
		
		String fixed = format.substring(0, firstTypeIndex);
		if(!value.startsWith(fixed)) {
			return null;
		}
		
		value = value.substring(firstTypeIndex);
		format = format.substring(firstTypeIndex + 1);
		
		int typeIndex = 0;
		int length = 0;
		Object object = null;
		for(char formatType : formatTypes) {
			try {
				typeIndex = format.indexOf(formatType);
				String lengthStr = format.substring(0, typeIndex);
				length = Integer.parseInt(lengthStr);
				
				switch(formatType) {
				case 'd':
					object = Integer.parseInt(value.substring(0, length).trim());
					break;
				case 's':
					object = value.substring(0, length);
					break;
				default:
					break;
				}
				break;
			} catch(IndexOutOfBoundsException | NumberFormatException e) {
				continue;
			}
		}
		if(object == null) {
			return null;
		}
		
		Object[] objects = {object};
		
		value = value.substring(length);
		format = format.substring(typeIndex + 1);
		
		return ArrayUtils.addAll(objects, unformat(value, format));
	}
}
