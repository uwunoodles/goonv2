package org.spongepowered.asm.lib;

public abstract class AnnotationVisitor {
   protected final int api;
   protected AnnotationVisitor av;

   public AnnotationVisitor(int a) {
      this(a, (AnnotationVisitor)null);
   }

   public AnnotationVisitor(int a, AnnotationVisitor a) {
      if (a != 262144 && a != 327680) {
         throw new IllegalArgumentException();
      } else {
         a.api = a;
         a.av = a;
      }
   }

   public void visit(String a, Object a) {
      if (a.av != null) {
         a.av.visit(a, a);
      }

   }

   public void visitEnum(String a, String a, String a) {
      if (a.av != null) {
         a.av.visitEnum(a, a, a);
      }

   }

   public AnnotationVisitor visitAnnotation(String a, String a) {
      return a.av != null ? a.av.visitAnnotation(a, a) : null;
   }

   public AnnotationVisitor visitArray(String a) {
      return a.av != null ? a.av.visitArray(a) : null;
   }

   public void visitEnd() {
      if (a.av != null) {
         a.av.visitEnd();
      }

   }
}
