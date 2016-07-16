package org.osgi.bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BundleManager {

	public static final Map<String, Bundle> bundles = new HashMap<String, Bundle>();

	public Bundle get(String type) {
		return bundles.get(type);
	}

	public static void set(String type, Bundle bundle) {
		bundles.put(type, bundle);
	}

	public static Class<?> getBeanClass(String name) {
		for (Bundle bundle : bundles.values())
			if (bundle.resource.classes.contains(name))
				return ClassCache.add(name, bundle.classLoader.loadClass(name));
		return null;
	}

	public static List<Class<?>> getBundle(String name) {
		List<Class<?>> list = new ArrayList<Class<?>>();
		for (Bundle bundle : bundles.values())
			if (bundle.resource.classes.contains(name))
				list.add(bundle.classLoader.loadClass(name));
		return list;
	}

}