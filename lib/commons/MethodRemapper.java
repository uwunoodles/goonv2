package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class MethodRemapper extends MethodVisitor {
   protected final Remapper remapper;

   public MethodRemapper(MethodVisitor a, Remapper a) {
      this(327680, a, a);
   }

   protected MethodRemapper(int a, MethodVisitor a, Remapper a) {
      super(a, a);
      a.remapper = a;
   }

   public AnnotationVisitor visitAnnotationDefault() {
      AnnotationVisitor a = super.visitAnnotationDefault();
      return (AnnotationVisitor)(a == null ? a : new AnnotationRemapper(a, a.remapper));
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      AnnotationVisitor a = super.visitAnnotation(a.remapper.mapDesc(a), a);
      return (AnnotationVisitor)(a == null ? a : new AnnotationRemapper(a, a.remapper));
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      AnnotationVisitor a = super.visitTypeAnnotation(a, a, a.remapper.mapDesc(a), a);
      return (AnnotationVisitor)(a == null ? a : new AnnotationRemapper(a, a.remapper));
   }

   public AnnotationVisitor visitParameterAnnotation(int a, String a, boolean a) {
      AnnotationVisitor a = super.visitParameterAnnotation(a, a.remapper.mapDesc(a), a);
      return (AnnotationVisitor)(a == null ? a : new AnnotationRemapper(a, a.remapper));
   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      super.visitFrame(a, a, a.remapEntries(a, a), a, a.remapEntries(a, a));
   }

   private Object[] remapEntries(int a, Object[] a) {
      for(int a = 0; a < a; ++a) {
         if (a[a] instanceof String) {
            Object[] a = new Object[a];
            if (a > 0) {
               System.arraycopy(a, 0, a, 0, a);
            }

            do {
               Object a = a[a];
               a[a++] = a instanceof String ? a.remapper.mapType((String)a) : a;
            } while(a < a);

            return a;
         }
      }

      return a;
   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      super.visitFieldInsn(a, a.remapper.mapType(a), a.remapper.mapFieldName(a, a, a), a.remapper.mapDesc(a));
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int a, String a, String a, String a) {
      if (a.api >= 327680) {
         super.visitMethodInsn(a, a, a, a);
      } else {
         a.doVisitMethodInsn(a, a, a, a, a == 185);
      }
   }

   public void visitMethodInsn(int a, String a, String a, String a, boolean a) {
      if (a.api < 327680) {
         super.visitMethodInsn(a, a, a, a, a);
      } else {
         a.doVisitMethodInsn(a, a, a, a, a);
      }
   }

   private void doVisitMethodInsn(int a, String a, String a, String a, boolean a) {
      if (a.mv != null) {
         a.mv.visitMethodInsn(a, a.remapper.mapType(a), a.remapper.mapMethodName(a, a, a), a.remapper.mapMethodDesc(a), a);
      }

   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      for(int a = 0; a < a.length; ++a) {
         a[a] = a.remapper.mapValue(a[a]);
      }

      super.visitInvokeDynamicInsn(a.remapper.mapInvokeDynamicMethodName(a, a), a.remapper.mapMethodDesc(a), (Handle)a.remapper.mapValue(a), a);
   }

   public void visitTypeInsn(int a, String a) {
      super.visitTypeInsn(a, a.remapper.mapType(a));
   }

   public void visitLdcInsn(Object a) {
      super.visitLdcInsn(a.remapper.mapValue(a));
   }

   public void visitMultiANewArrayInsn(String a, int a) {
      super.visitMultiANewArrayInsn(a.remapper.mapDesc(a), a);
   }

   public AnnotationVisitor visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      AnnotationVisitor a = super.visitInsnAnnotation(a, a, a.remapper.mapDesc(a), a);
      return (AnnotationVisitor)(a == null ? a : new AnnotationRemapper(a, a.remapper));
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      super.visitTryCatchBlock(a, a, a, a == null ? null : a.remapper.mapType(a));
   }

   public AnnotationVisitor visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      AnnotationVisitor a = super.visitTryCatchAnnotation(a, a, a.remapper.mapDesc(a), a);
      return (AnnotationVisitor)(a == null ? a : new AnnotationRemapper(a, a.remapper));
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      super.visitLocalVariable(a, a.remapper.mapDesc(a), a.remapper.mapSignature(a, true), a, a, a);
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      AnnotationVisitor a = super.visitLocalVariableAnnotation(a, a, a, a, a, a.remapper.mapDesc(a), a);
      return (AnnotationVisitor)(a == null ? a : new AnnotationRemapper(a, a.remapper));
   }
}
