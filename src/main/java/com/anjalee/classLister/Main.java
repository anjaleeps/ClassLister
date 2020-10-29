package com.anjalee.classLister;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import java.io.IOException;

public class Main
{
    public static void main( String[] args ) throws IOException
    {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("com.anjalee.classLister.ClassPrinter");
        cr.accept(cp, ClassReader.EXPAND_FRAMES);
    }
}
