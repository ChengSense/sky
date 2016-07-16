package org.osgi.bundle;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClassCache {

	private static final Map<String, Class<?>> map = new LinkedHashMap<String, Class<?>>();

	public static Class<?> get(String key) {
		return map.get(key);
	}

	public static Class<?> add(String key, Class<?> clas) {
		return map.put(key, clas);
	}

}
