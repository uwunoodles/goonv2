package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public final class TraceMethodVisitor extends MethodVisitor {
   public final Printer p;

   public TraceMethodVisitor(Printer a) {
      this((MethodVisitor)null, a);
   }

   public TraceMethodVisitor(MethodVisitor a, Printer a) {
      super(327680, a);
      a.p = a;
   }

   public void visitParameter(String a, int a) {
      a.p.visitParameter(a, a);
      super.visitParameter(a, a);
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      Printer a = a.p.visitMethodAnnotation(a, a);
      AnnotationVisitor a = a.mv == null ? null : a.mv.visitAnnotation(a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      Printer a = a.p.visitMethodTypeAnnotation(a, a, a, a);
      AnnotationVisitor a = a.mv == null ? null : a.mv.visitTypeAnnotation(a, a, a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitAttribute(Attribute a) {
      a.p.visitMethodAttribute(a);
      super.visitAttribute(a);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      Printer a = a.p.visitAnnotationDefault();
      AnnotationVisitor a = a.mv == null ? null : a.mv.visitAnnotationDefault();
      return new TraceAnnotationVisitor(a, a);
   }

   public AnnotationVisitor visitParameterAnnotation(int a, String a, boolean a) {
      Printer a = a.p.visitParameterAnnotation(a, a, a);
      AnnotationVisitor a = a.mv == null ? null : a.mv.visitParameterAnnotation(a, a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitCode() {
      a.p.visitCode();
      super.visitCode();
   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      a.p.visitFrame(a, a, a, a, a);
      super.visitFrame(a, a, a, a, a);
   }

   public void visitInsn(int a) {
      a.p.visitInsn(a);
      super.visitInsn(a);
   }

   public void visitIntInsn(int a, int a) {
      a.p.visitIntInsn(a, a);
      super.visitIntInsn(a, a);
   }

   public void visitVarInsn(int a, int a) {
      a.p.visitVarInsn(a, a);
      super.visitVarInsn(a, a);
   }

   public void visitTypeInsn(int a, String a) {
      a.p.visitTypeInsn(a, a);
      super.visitTypeInsn(a, a);
   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      a.p.visitFieldInsn(a, a, a, a);
      super.visitFieldInsn(a, a, a, a);
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int a, String a, String a, String a) {
      if (a.api >= 327680) {
         super.visitMethodInsn(a, a, a, a);
      } else {
         a.p.visitMethodInsn(a, a, a, a);
         if (a.mv != null) {
            a.mv.visitMethodInsn(a, a, a, a);
         }

      }
   }

   public void visitMethodInsn(int a, String a, String a, String a, boolean a) {
      if (a.api < 327680) {
         super.visitMethodInsn(a, a, a, a, a);
      } else {
         a.p.visitMethodInsn(a, a, a, a, a);
         if (a.mv != null) {
            a.mv.visitMethodInsn(a, a, a, a, a);
         }

      }
   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      a.p.visitInvokeDynamicInsn(a, a, a, a);
      super.visitInvokeDynamicInsn(a, a, a, a);
   }

   public void visitJumpInsn(int a, Label a) {
      a.p.visitJumpInsn(a, a);
      super.visitJumpInsn(a, a);
   }

   public void visitLabel(Label a) {
      a.p.visitLabel(a);
      super.visitLabel(a);
   }

   public void visitLdcInsn(Object a) {
      a.p.visitLdcInsn(a);
      super.visitLdcInsn(a);
   }

   public void visitIincInsn(int a, int a) {
      a.p.visitIincInsn(a, a);
      super.visitIincInsn(a, a);
   }

   public void visitTableSwitchInsn(int a, int a, Label a, Label... a) {
      a.p.visitTableSwitchInsn(a, a, a, a);
      super.visitTableSwitchInsn(a, a, a, a);
   }

   public void visitLookupSwitchInsn(Label a, int[] a, Label[] a) {
      a.p.visitLookupSwitchInsn(a, a, a);
      super.visitLookupSwitchInsn(a, a, a);
   }

   public void visitMultiANewArrayInsn(String a, int a) {
      a.p.visitMultiANewArrayInsn(a, a);
      super.visitMultiANewArrayInsn(a, a);
   }

   public AnnotationVisitor visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      Printer a = a.p.visitInsnAnnotation(a, a, a, a);
      AnnotationVisitor a = a.mv == null ? null : a.mv.visitInsnAnnotation(a, a, a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      a.p.visitTryCatchBlock(a, a, a, a);
      super.visitTryCatchBlock(a, a, a, a);
   }

   public AnnotationVisitor visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      Printer a = a.p.visitTryCatchAnnotation(a, a, a, a);
      AnnotationVisitor a = a.mv == null ? null : a.mv.visitTryCatchAnnotation(a, a, a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      a.p.visitLocalVariable(a, a, a, a, a, a);
      super.visitLocalVariable(a, a, a, a, a, a);
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      Printer a = a.p.visitLocalVariableAnnotation(a, a, a, a, a, a, a);
      AnnotationVisitor a = a.mv == null ? null : a.mv.visitLocalVariableAnnotation(a, a, a, a, a, a, a);
      return new TraceAnnotationVisitor(a, a);
   }

   public void visitLineNumber(int a, Label a) {
      a.p.visitLineNumber(a, a);
      super.visitLineNumber(a, a);
   }

   public void visitMaxs(int a, int a) {
      a.p.visitMaxs(a, a);
      super.visitMaxs(a, a);
   }

   public void visitEnd() {
      a.p.visitMethodEnd();
      super.visitEnd();
   }
}
