package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;

public class AnnotationRemapper extends AnnotationVisitor {
   protected final Remapper remapper;

   public AnnotationRemapper(AnnotationVisitor a, Remapper a) {
      this(327680, a, a);
   }

   protected AnnotationRemapper(int a, AnnotationVisitor a, Remapper a) {
      super(a, a);
      a.remapper = a;
   }

   public void visit(String a, Object a) {
      a.av.visit(a, a.remapper.mapValue(a));
   }

   public void visitEnum(String a, String a, String a) {
      a.av.visitEnum(a, a.remapper.mapDesc(a), a);
   }

   public AnnotationVisitor visitAnnotation(String a, String a) {
      AnnotationVisitor a = a.av.visitAnnotation(a, a.remapper.mapDesc(a));
      return a == null ? null : (a == a.av ? a : new AnnotationRemapper(a, a.remapper));
   }

   public AnnotationVisitor visitArray(String a) {
      AnnotationVisitor a = a.av.visitArray(a);
      return a == null ? null : (a == a.av ? a : new AnnotationRemapper(a, a.remapper));
   }
}
