package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public final class TraceClassVisitor extends ClassVisitor {
   private final PrintWriter pw;
   public final Printer p;

   public TraceClassVisitor(PrintWriter a) {
      this((ClassVisitor)null, a);
   }

   public TraceClassVisitor(ClassVisitor a, PrintWriter a) {
      this(a, new Textifier(), a);
   }

   public TraceClassVisitor(ClassVisitor a, Printer a, PrintWriter a) {
      super(327680, a);
      a.pw = a;
      a.p = a;
   }

   public void visit(int a, int a, String a, String a, String a, String[] a) {
      a.p.visit(a, a, a, a, a, a);
      super.visit(a, a, a, a, a, a);
   }

   public void visitSource(String a, String a) {
      a.p.visitSource(a, a);
      super.visitSource(a, a);
   }

   public void visitOuterClass(String a, String a, String a) {
      a.p.visitOuterClass(a, a, a);
      super.visitOuterClass(a, a, a);
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      Printer a = a.p.visitClassAnnotation(a, a);
      AnnotationVisitor a = a.cv == null ? null : a.cv.visitAnnotation(a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      Printer a = a.p.visitClassTypeAnnotation(a, a, a, a);
      AnnotationVisitor a = a.cv == null ? null : a.cv.visitTypeAnnotation(a, a, a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitAttribute(Attribute a) {
      a.p.visitClassAttribute(a);
      super.visitAttribute(a);
   }

   public void visitInnerClass(String a, String a, String a, int a) {
      a.p.visitInnerClass(a, a, a, a);
      super.visitInnerClass(a, a, a, a);
   }

   public FieldVisitor visitField(int a, String a, String a, String a, Object a) {
      Printer a = a.p.visitField(a, a, a, a, a);
      FieldVisitor a = a.cv == null ? null : a.cv.visitField(a, a, a, a, a);
      return new TraceFieldVisitor(a, a);
   }

   public MethodVisitor visitMethod(int a, String a, String a, String a, String[] a) {
      Printer a = a.p.visitMethod(a, a, a, a, a);
      MethodVisitor a = a.cv == null ? null : a.cv.visitMethod(a, a, a, a, a);
      return new TraceMethodVisitor(a, a);
   }

   public void visitEnd() {
      a.p.visitClassEnd();
      if (a.pw != null) {
         a.p.print(a.pw);
         a.pw.flush();
      }

      super.visitEnd();
   }
}
