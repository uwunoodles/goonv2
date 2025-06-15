package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;

public final class TraceAnnotationVisitor extends AnnotationVisitor {
   private final Printer p;

   public TraceAnnotationVisitor(Printer a) {
      this((AnnotationVisitor)null, a);
   }

   public TraceAnnotationVisitor(AnnotationVisitor a, Printer a) {
      super(327680, a);
      a.p = a;
   }

   public void visit(String a, Object a) {
      a.p.visit(a, a);
      super.visit(a, a);
   }

   public void visitEnum(String a, String a, String a) {
      a.p.visitEnum(a, a, a);
      super.visitEnum(a, a, a);
   }

   public AnnotationVisitor visitAnnotation(String a, String a) {
      Printer a = a.p.visitAnnotation(a, a);
      AnnotationVisitor a = a.av == null ? null : a.av.visitAnnotation(a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public AnnotationVisitor visitArray(String a) {
      Printer a = a.p.visitArray(a);
      AnnotationVisitor a = a.av == null ? null : a.av.visitArray(a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitEnd() {
      a.p.visitAnnotationEnd();
      super.visitEnd();
   }
}
