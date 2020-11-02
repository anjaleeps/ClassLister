package com.anjalee.classLister;

import org.objectweb.asm.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.objectweb.asm.Opcodes.ASM6;

public class ClassPrinter extends ClassVisitor {

    Set<String> classes = new HashSet<>();

    public ClassPrinter() {

        super(ASM6);
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//        System.out.println(name+ " extends " + superName + " {");
        classes.add(name);
        classes.add(superName);
        for (String intfc : interfaces) {
            classes.add(intfc);
        }
        //        cv.visit(version, access, name, signature, superName, interfaces);
    }

    public void visitSource(String source, String debug) {

    }

    public ModuleVisitor visitModule(String name, int access, String version) {

        System.out.println(name);
        return null;
    }

    public void visitOuterClass(String owner, String name, String description) {
//        System.out.println("outerclass " + owner + " " + name + " " + description);
        classes.add(owner);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//        System.out.println("annotation " + desc);
        return null;
    }

    public void visitAttribute(Attribute attr) {

    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
//        System.out.println(name);
        classes.add(name);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {

        if (desc.length() > 1) {
//            System.out.println(desc.substring(1));
            classes.add(desc.substring(1, desc.length() - 1));
        }
        return new FieldPrinter();
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//        System.out.println(desc);
        return new MethodPrinter();
    }

    public void visitEnd() {

        Iterator<String> clz = classes.iterator();

        while (clz.hasNext()) {
            System.out.println(clz.next());
        }
    }

    public class MethodPrinter extends MethodVisitor {

        public MethodPrinter() {

            super(ASM6);
        }

        public void visitParameter(String name, int access) {

        }

        public void visitAttribute(Attribute attr) {

        }

        public void visitTypeInsn(int opcode, String type) {
//            System.out.println(type);
            classes.add(type);
        }

        public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {

        }

        public void visitFrameInsn(int opcode, String owner, String name, String desc) {

            if (desc.length() > 1) {
//                System.out.println(desc.substring(1));
                classes.add(desc.substring(1));
            }
        }

        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {

            Character i = desc.charAt(0);
            String desc0 = desc.split(";")[0];
            if (i.equals('L')) {
                desc0 = desc0.substring(1);
                classes.add(desc0);
            } else if (i.equals('[') && desc0.length() > 2) {
                desc0 = desc0.substring(2);
                classes.add(desc0);
            }
        }

        public void visitCode() {

        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
//            System.out.println(owner + " " + name + " " + desc);
            classes.add(owner);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//            System.out.println(desc);
            classes.add(owner);
        }

        public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
//            System.out.println("dyn " + desc);
        }

        public void visitEnd() {

        }
    }

    public class FieldPrinter extends FieldVisitor {

        public FieldPrinter() {

            super(ASM6);
        }

        public void visitAttribute(Attribute attr) {
//            System.out.println("attr" + attr.type);
        }

        public void visitEnd() {

        }
    }
}
