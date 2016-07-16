package org.osgi.bundle;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import com.google.common.io.ByteStreams;

public class DependenciesDefinition {

	private static Bundle bundle;
	private static Resource resource;
	private static JarEntry jarEntry;
	private static InputStream input;
	private static BundleClassLoader classLoader;
	private static JarInputStream jarInput;
	public static Set<String> dependencies;
	private static String url = ClassLoader.getSystemClassLoader().getResource("bundles").getPath();

	public static void execute() {
		try {
			url = URLDecoder.decode(url, "utf-8");
			new File(url).listFiles(new FileFilter() {
				public boolean accept(File file) {
					if (!file.getName().endsWith(Express.SUFFIXES_JAR))
						return false;
					resource = new Resource();
					classLoader = new BundleClassLoader(file.getPath());
					bundle = new Bundle(resource, classLoader);
					BundleManager.set(file.getName(), bundle);
					dependencies = new HashSet<String>();
					parse(file);
					bundle.setRequires(dependencies);
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parse(File file) {
		try {
			Set set;
			jarInput = new JarInputStream(new FileInputStream(file));
			while ((jarEntry = jarInput.getNextJarEntry()) != null) {
				String name = jarEntry.getName();
				switch (Express.get(name)) {
				case Express.JAR:
					// input = new JarFile(file).getInputStream(jarEntry);
					// parse(new NewFile(input));
					break;
				case Express.CLASS:
					input = new JarFile(file).getInputStream(jarEntry);
					resource.setClasses(name);
					byte[] bytes = ByteStreams.toByteArray(input);
					ClassDependencies cd= new ClassDependencies(bytes);
					bundle.init(cd);
					dependencies.addAll((Set)cd);
					break;
				default:
					resource.setStatics(name);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}