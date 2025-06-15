package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class ClassRemapper extends ClassVisitor {
   protected final Remapper remapper;
   protected String className;

   public ClassRemapper(ClassVisitor a, Remapper a) {
      this(327680, a, a);
   }

   protected ClassRemapper(int a, ClassVisitor a, Remapper a) {
      super(a, a);
      a.remapper = a;
   }

   public void visit(int a, int a, String a, String a, String a, String[] a) {
      a.className = a;
      super.visit(a, a, a.remapper.mapType(a), a.remapper.mapSignature(a, false), a.remapper.mapType(a), a == null ? null : a.remapper.mapTypes(a));
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      AnnotationVisitor a = super.visitAnnotation(a.remapper.mapDesc(a), a);
      return a == null ? null : a.createAnnotationRemapper(a);
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      AnnotationVisitor a = super.visitTypeAnnotation(a, a, a.remapper.mapDesc(a), a);
      return a == null ? null : a.createAnnotationRemapper(a);
   }

   public FieldVisitor visitField(int a, String a, String a, String a, Object a) {
      FieldVisitor a = super.visitField(a, a.remapper.mapFieldName(a.className, a, a), a.remapper.mapDesc(a), a.remapper.mapSignature(a, true), a.remapper.mapValue(a));
      return a == null ? null : a.createFieldRemapper(a);
   }

   public MethodVisitor visitMethod(int a, String a, String a, String a, String[] a) {
      String a = a.remapper.mapMethodDesc(a);
      MethodVisitor a = super.visitMethod(a, a.remapper.mapMethodName(a.className, a, a), a, a.remapper.mapSignature(a, false), a == null ? null : a.remapper.mapTypes(a));
      return a == null ? null : a.createMethodRemapper(a);
   }

   public void visitInnerClass(String a, String a, String a, int a) {
      super.visitInnerClass(a.remapper.mapType(a), a == null ? null : a.remapper.mapType(a), a, a);
   }

   public void visitOuterClass(String a, String a, String a) {
      super.visitOuterClass(a.remapper.mapType(a), a == null ? null : a.remapper.mapMethodName(a, a, a), a == null ? null : a.remapper.mapMethodDesc(a));
   }

   protected FieldVisitor createFieldRemapper(FieldVisitor a) {
      return new FieldRemapper(a, a.remapper);
   }

   protected MethodVisitor createMethodRemapper(MethodVisitor a) {
      return new MethodRemapper(a, a.remapper);
   }

   protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor a) {
      return new AnnotationRemapper(a, a.remapper);
   }
}
