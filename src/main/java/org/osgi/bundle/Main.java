package org.osgi.bundle;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Main implements Opcodes {

	public byte[] code() {
		ClassWriter cw = new ClassWriter(0);
		MethodVisitor mv;

		cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "org/osgi/bundle/Launcher", null, "java/lang/Object", null);
		try {
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitTypeInsn(NEW, "org/springframework/context/support/ClassPathXmlApplicationContext");
			mv.visitInsn(DUP);
			mv.visitLdcInsn("classpath:spring.xml");
			mv.visitMethodInsn(INVOKESPECIAL, "org/springframework/context/support/ClassPathXmlApplicationContext", "<init>", "(Ljava/lang/String;)V", false);
			mv.visitVarInsn(ASTORE, 1);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/springframework/context/support/ClassPathXmlApplicationContext", "getBeanFactory", "()Lorg/springframework/beans/factory/config/ConfigurableListableBeanFactory;", false);
			mv.visitLdcInsn(Type.getType("Lorg/osgi/bundle/Launcher;"));
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getClassLoader", "()Ljava/lang/ClassLoader;", false);
			mv.visitMethodInsn(INVOKEINTERFACE, "org/springframework/beans/factory/config/ConfigurableListableBeanFactory", "setBeanClassLoader", "(Ljava/lang/ClassLoader;)V", true);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn("serviceImpl");
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/springframework/context/support/ClassPathXmlApplicationContext", "getBean", "(Ljava/lang/String;)Ljava/lang/Object;", false);
			mv.visitTypeInsn(CHECKCAST, "org/bundle/Service");
			mv.visitVarInsn(ASTORE, 2);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEINTERFACE, "org/bundle/Service", "active", "()V", true);
			mv.visitInsn(RETURN);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		} catch (Exception e) {
		}
		return cw.toByteArray();
	}
}
