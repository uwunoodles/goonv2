package org.spongepowered.asm.lib;

public abstract class ClassVisitor {
   protected final int api;
   protected ClassVisitor cv;

   public ClassVisitor(int a) {
      this(a, (ClassVisitor)null);
   }

   public ClassVisitor(int a, ClassVisitor a) {
      if (a != 262144 && a != 327680) {
         throw new IllegalArgumentException();
      } else {
         a.api = a;
         a.cv = a;
      }
   }

   public void visit(int a, int a, String a, String a, String a, String[] a) {
      if (a.cv != null) {
         a.cv.visit(a, a, a, a, a, a);
      }

   }

   public void visitSource(String a, String a) {
      if (a.cv != null) {
         a.cv.visitSource(a, a);
      }

   }

   public void visitOuterClass(String a, String a, String a) {
      if (a.cv != null) {
         a.cv.visitOuterClass(a, a, a);
      }

   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      return a.cv != null ? a.cv.visitAnnotation(a, a) : null;
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      if (a.api < 327680) {
         throw new RuntimeException();
      } else {
         return a.cv != null ? a.cv.visitTypeAnnotation(a, a, a, a) : null;
      }
   }

   public void visitAttribute(Attribute a) {
      if (a.cv != null) {
         a.cv.visitAttribute(a);
      }

   }

   public void visitInnerClass(String a, String a, String a, int a) {
      if (a.cv != null) {
         a.cv.visitInnerClass(a, a, a, a);
      }

   }

   public FieldVisitor visitField(int a, String a, String a, String a, Object a) {
      return a.cv != null ? a.cv.visitField(a, a, a, a, a) : null;
   }

   public MethodVisitor visitMethod(int a, String a, String a, String a, String[] a) {
      return a.cv != null ? a.cv.visitMethod(a, a, a, a, a) : null;
   }

   public void visitEnd() {
      if (a.cv != null) {
         a.cv.visitEnd();
      }

   }
}
