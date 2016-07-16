package org.osgi.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Maping {
	static final Map<String, Method> methods = new HashMap<String, Method>();
	static final String PATH_PARAM_REGEX = "^\\{[^(\\{|\\})].*\\}$";
	static final String PATH_PARAM_KEY_REGEX = "[\\{,\\}]";
	static final String PATH_PARAM_BLANK_REGEX = "\\s*/\\s*";
	static final String SPRIT_REGEX = "/";
	static String KEY;
	static String VALUE;

	public static Method get(String url) {
		url = trim(url);
		Method method = methods.get(url);
		if (method != null)
			return method;

		for (String key : methods.keySet())
			if (match(key, url))
				return methods.get(key);
		return method;
	}

	public static void set(String key, Method method) {
		methods.put(trim(key), method);
	}

	public static String trim(String str) {
		return str.trim().replaceAll(PATH_PARAM_BLANK_REGEX, SPRIT_REGEX);
	}

	public static boolean match(String key, String url) {
		String[] a = key.split(SPRIT_REGEX), b = url.split(SPRIT_REGEX);
		if (a.length != b.length)
			return false;
		for (int i = 0; i < b.length; i++) {
			if (a[i].matches(PATH_PARAM_REGEX))
				continue;
			if (!a[i].equals(b[i]))
				return false;
		}
		return true;
	}
}