package org.osgi.util;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Main2 implements Opcodes {

	public byte[] code(String config) {
		ClassWriter cw = new ClassWriter(0);
		MethodVisitor mv;
		try {
			cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "org/bundle/service/Maina", null, "java/lang/Object", null);
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitLabel(l0);
			mv.visitTypeInsn(NEW, "org/springframework/context/support/ClassPathXmlApplicationContext");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(config);
			mv.visitMethodInsn(INVOKESPECIAL, "org/springframework/context/support/ClassPathXmlApplicationContext", "<init>", "(Ljava/lang/String;)V", false);
			mv.visitVarInsn(ASTORE, 1);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/springframework/context/support/ClassPathXmlApplicationContext", "getBeanFactory", "()Lorg/springframework/beans/factory/config/ConfigurableListableBeanFactory;", false);
			mv.visitLdcInsn(Type.getType("Lorg/bundle/service/Maina;"));
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getClassLoader", "()Ljava/lang/ClassLoader;", false);
			mv.visitMethodInsn(INVOKEINTERFACE, "org/springframework/beans/factory/config/ConfigurableListableBeanFactory", "setBeanClassLoader", "(Ljava/lang/ClassLoader;)V", true);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn("servicesImpl");
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/springframework/context/support/ClassPathXmlApplicationContext", "getBean", "(Ljava/lang/String;)Ljava/lang/Object;", false);
			mv.visitTypeInsn(CHECKCAST, "org/bundlea/ServiceaImpl");
			mv.visitVarInsn(ASTORE, 2);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/bundlea/ServiceaImpl", "active", "()V", false);
			mv.visitLabel(l1);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l2);
			mv.visitFrame(Opcodes.F_FULL, 1, new Object[] { "org/bundle/service/Maina" }, 1, new Object[] { "java/lang/Exception" });
			mv.visitVarInsn(ASTORE, 1);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false);
			mv.visitLabel(l3);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(RETURN);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
			cw.visitEnd();
		} catch (Exception e) {
		}
		return cw.toByteArray();
	}
}
