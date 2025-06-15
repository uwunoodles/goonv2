package org.spongepowered.asm.lib;

public abstract class FieldVisitor {
   protected final int api;
   protected FieldVisitor fv;

   public FieldVisitor(int a) {
      this(a, (FieldVisitor)null);
   }

   public FieldVisitor(int a, FieldVisitor a) {
      if (a != 262144 && a != 327680) {
         throw new IllegalArgumentException();
      } else {
         a.api = a;
         a.fv = a;
      }
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      return a.fv != null ? a.fv.visitAnnotation(a, a) : null;
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      if (a.api < 327680) {
         throw new RuntimeException();
      } else {
         return a.fv != null ? a.fv.visitTypeAnnotation(a, a, a, a) : null;
      }
   }

   public void visitAttribute(Attribute a) {
      if (a.fv != null) {
         a.fv.visitAttribute(a);
      }

   }

   public void visitEnd() {
      if (a.fv != null) {
         a.fv.visitEnd();
      }

   }
}
