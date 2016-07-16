package org.osgi.bundle;

import java.util.HashSet;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class ClassDependencies extends HashSet<Object> {
	private static final long serialVersionUID = 1L;
	private String className;
	private byte[] bytes;

	public ClassDependencies(byte[] bytes) {
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);

		setClassName(Express.disClass(cn.name));
		setBytes(bytes);
		visitAnnotation(cn.visibleAnnotations);
		visitAnnotation(cn.visibleTypeAnnotations);
		visitField(cn.fields);
		visitMethod(cn.methods);
	}

	public void visitField(List<FieldNode> list) {
		for (FieldNode fiel : list) {
			visitAnnotation(fiel.visibleTypeAnnotations);
			visitAnnotation(fiel.visibleAnnotations);
		}
	}

	public void visitMethod(List<MethodNode> list) {
		for (MethodNode method : list) {
			visitAnnotation(method.visibleAnnotations);
			visitAnnotation(method.visibleTypeAnnotations);
			visitParamAnnotation(method.visibleParameterAnnotations);
			visitAnnotation(method.visibleLocalVariableAnnotations);
			visitLocalVariable(method.localVariables);
			visitException(method.exceptions);
			visitInsnNode(method.instructions);
		}
	}

	public void visitAnnotation(List<?> list) {
		if (list != null) {
			for (Object object : list)
				set(((AnnotationNode) object).desc);
		}
	}

	public void visitException(List<String> List) {
		if (List != null) {
			for (String variable : List)
				set(variable);
		}
	}

	public void visitParamAnnotation(List<AnnotationNode>[] array) {
		if (array != null) {
			for (List<AnnotationNode> list : array)
				visitAnnotation(list);
		}
	}

	public void visitLocalVariable(List<LocalVariableNode> list) {
		if (list != null) {
			for (LocalVariableNode variable : list)
				set(variable.desc);
		}
	}

	public void visitInsnNode(InsnList list) {
		if (list != null) {
			visitInsnNode(list.getFirst());
		}
	}

	public void visitInsnNode(AbstractInsnNode insnNode) {
		if (insnNode != null && insnNode.getNext() != null) {
			if (insnNode instanceof TypeInsnNode)
				set(((TypeInsnNode) insnNode).desc);
			if (insnNode instanceof FieldInsnNode)
				set(((FieldInsnNode) insnNode).desc);
			visitInsnNode(insnNode.getNext());
		}
	}

	public void set(String desc) {
		if (desc != null) {
			desc = Express.disClass(desc);
			if (!desc.matches(Express.EXPRESS_BASE_TYPE)) {
				desc = desc.replaceFirst(Express.EXPRESS_OBJECT, "");
				if (!desc.matches(Express.EXPRESS_BASE_SYSTEM)) {
					this.add(desc);
				}
			}
		}
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}