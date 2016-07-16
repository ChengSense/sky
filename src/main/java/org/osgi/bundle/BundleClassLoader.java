package org.osgi.bundle;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.io.ByteStreams;

public class BundleClassLoader extends ClassLoader {

	private List<String> urls;
	private List<JarFile> jarFiles;

	public BundleClassLoader(String url) {
		this(new String[] { url });
	}

	public BundleClassLoader(String[] urls) {
		super(null);
		try {
			this.urls = Arrays.asList(urls);
			this.jarFiles = new ArrayList<JarFile>();
			for (String file : this.urls)
				jarFiles.add(new JarFile(new File(file)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Class<?> loadClass(String name, byte[] bytes) {
		try {
			Class<?> clas = defineClass(name, bytes, 0, bytes.length);
			ClassCache.add(name, clas);
			return clas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Class<?> loadClass(String name) {
		try {
			Class<?> clas = findLoadedClass(name);
			if (clas != null)
				return clas;
			if (Express.filter(name))
				clas = Class.forName(name);
			if (clas != null)
				return clas;
			clas = ClassCache.get(name);
			if (clas != null)
				return clas;
			clas = defineClass(name);
			if (clas != null)
				return clas;
			clas = BundleManager.getBeanClass(name);
			if (clas != null)
				return clas;
			clas = Class.forName(name);
			if (clas != null)
				return clas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Class<?> defineClass(String name) {
		try {
			for (JarFile jarFile : jarFiles) {
				JarEntry jarEntry = jarFile.getJarEntry(Express.enClass(name));
				if (jarEntry != null) {
					InputStream input = jarFile.getInputStream(jarEntry);
					byte[] bytes = ByteStreams.toByteArray(input);
					Class<?> clas = defineClass(name, bytes, 0, bytes.length);
					ClassCache.add(name, clas);
					return clas;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}