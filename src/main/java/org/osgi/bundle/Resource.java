package org.osgi.bundle;

import java.util.HashSet;
import java.util.Set;

public class Resource {
	public final Set<String> classes = new HashSet<String>();

	public final Set<String> statics = new HashSet<String>();

	public Set<String> getClasses() {
		return classes;
	}

	public void setClasses(String str) {
		this.classes.add(Express.disClass(str));
	}

	public Set<String> getStatics() {
		return statics;
	}

	public void setStatics(String statics) {
		this.statics.add(statics);
	}
}