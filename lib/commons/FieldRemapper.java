package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public class FieldRemapper extends FieldVisitor {
   private final Remapper remapper;

   public FieldRemapper(FieldVisitor a, Remapper a) {
      this(327680, a, a);
   }

   protected FieldRemapper(int a, FieldVisitor a, Remapper a) {
      super(a, a);
      a.remapper = a;
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      AnnotationVisitor a = a.fv.visitAnnotation(a.remapper.mapDesc(a), a);
      return a == null ? null : new AnnotationRemapper(a, a.remapper);
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      AnnotationVisitor a = super.visitTypeAnnotation(a, a, a.remapper.mapDesc(a), a);
      return a == null ? null : new AnnotationRemapper(a, a.remapper);
   }
}
