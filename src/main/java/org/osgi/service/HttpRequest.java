package org.osgi.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.osgi.annotion.When;

public class HttpRequest {

	public static void getMaping() {
		Class<?> clas = Object.class;
		Annotation ann = clas.getAnnotation(When.class);
		Method[] clasMethods = clas.getDeclaredMethods();
		for (Method method : clasMethods) {
			Annotation anno = method.getAnnotation(When.class);
			Maping.set(((When) ann).value() + ((When) anno).value(), method);
		}
		System.out.println(Maping.methods);
	}

	public static void execute(String url) {
		Method method = Maping.get(url);
		Class<?> clas = method.getDeclaringClass();
		try {
			method.invoke(clas.newInstance(), "1111111");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getMaping();
		execute("/service/active/1111111");
	}
}