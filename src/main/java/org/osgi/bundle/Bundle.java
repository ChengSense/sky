package org.osgi.bundle;

import java.util.HashSet;
import java.util.Set;

import org.osgi.annotion.Bunble;

public class Bundle {
	private String name;
	private String version = "1.0.0";
	private Set<String> requires;
	public Resource resource;
	public transient BundleClassLoader classLoader;

	public Bundle(Resource resource, BundleClassLoader classLoader) {
		this.resource = resource;
		this.classLoader = classLoader;
		this.requires = new HashSet<String>();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Set<String> getRequires() {
		return requires;
	}

	public void setRequires(Set<String> dependencies) {
		this.requires.addAll(dependencies);
		this.requires.removeAll(resource.classes);
	}

	public void init(ClassDependencies cd) {
		if (!cd.contains(Bunble.class.getName()))
			return;
		setName(cd.getClassName());
		resource.classes.add(Express.MAIN_CLASS);
		classLoader.loadClass(Express.MAIN_CLASS, new Main().code());
	}
}