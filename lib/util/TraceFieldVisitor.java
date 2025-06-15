package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public final class TraceFieldVisitor extends FieldVisitor {
   public final Printer p;

   public TraceFieldVisitor(Printer a) {
      this((FieldVisitor)null, a);
   }

   public TraceFieldVisitor(FieldVisitor a, Printer a) {
      super(327680, a);
      a.p = a;
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      Printer a = a.p.visitFieldAnnotation(a, a);
      AnnotationVisitor a = a.fv == null ? null : a.fv.visitAnnotation(a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      Printer a = a.p.visitFieldTypeAnnotation(a, a, a, a);
      AnnotationVisitor a = a.fv == null ? null : a.fv.visitTypeAnnotation(a, a, a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitAttribute(Attribute a) {
      a.p.visitFieldAttribute(a);
      super.visitAttribute(a);
   }

   public void visitEnd() {
      a.p.visitFieldEnd();
      super.visitEnd();
   }
}
