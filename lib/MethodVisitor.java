package org.spongepowered.asm.lib;

public abstract class MethodVisitor {
   protected final int api;
   protected MethodVisitor mv;

   public MethodVisitor(int a) {
      this(a, (MethodVisitor)null);
   }

   public MethodVisitor(int a, MethodVisitor a) {
      if (a != 262144 && a != 327680) {
         throw new IllegalArgumentException();
      } else {
         a.api = a;
         a.mv = a;
      }
   }

   public void visitParameter(String a, int a) {
      if (a.api < 327680) {
         throw new RuntimeException();
      } else {
         if (a.mv != null) {
            a.mv.visitParameter(a, a);
         }

      }
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return a.mv != null ? a.mv.visitAnnotationDefault() : null;
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      return a.mv != null ? a.mv.visitAnnotation(a, a) : null;
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      if (a.api < 327680) {
         throw new RuntimeException();
      } else {
         return a.mv != null ? a.mv.visitTypeAnnotation(a, a, a, a) : null;
      }
   }

   public AnnotationVisitor visitParameterAnnotation(int a, String a, boolean a) {
      return a.mv != null ? a.mv.visitParameterAnnotation(a, a, a) : null;
   }

   public void visitAttribute(Attribute a) {
      if (a.mv != null) {
         a.mv.visitAttribute(a);
      }

   }

   public void visitCode() {
      if (a.mv != null) {
         a.mv.visitCode();
      }

   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      if (a.mv != null) {
         a.mv.visitFrame(a, a, a, a, a);
      }

   }

   public void visitInsn(int a) {
      if (a.mv != null) {
         a.mv.visitInsn(a);
      }

   }

   public void visitIntInsn(int a, int a) {
      if (a.mv != null) {
         a.mv.visitIntInsn(a, a);
      }

   }

   public void visitVarInsn(int a, int a) {
      if (a.mv != null) {
         a.mv.visitVarInsn(a, a);
      }

   }

   public void visitTypeInsn(int a, String a) {
      if (a.mv != null) {
         a.mv.visitTypeInsn(a, a);
      }

   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      if (a.mv != null) {
         a.mv.visitFieldInsn(a, a, a, a);
      }

   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int a, String a, String a, String a) {
      if (a.api >= 327680) {
         boolean a = a == 185;
         a.visitMethodInsn(a, a, a, a, a);
      } else {
         if (a.mv != null) {
            a.mv.visitMethodInsn(a, a, a, a);
         }

      }
   }

   public void visitMethodInsn(int a, String a, String a, String a, boolean a) {
      if (a.api < 327680) {
         if (a != (a == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
         } else {
            a.visitMethodInsn(a, a, a, a);
         }
      } else {
         if (a.mv != null) {
            a.mv.visitMethodInsn(a, a, a, a, a);
         }

      }
   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      if (a.mv != null) {
         a.mv.visitInvokeDynamicInsn(a, a, a, a);
      }

   }

   public void visitJumpInsn(int a, Label a) {
      if (a.mv != null) {
         a.mv.visitJumpInsn(a, a);
      }

   }

   public void visitLabel(Label a) {
      if (a.mv != null) {
         a.mv.visitLabel(a);
      }

   }

   public void visitLdcInsn(Object a) {
      if (a.mv != null) {
         a.mv.visitLdcInsn(a);
      }

   }

   public void visitIincInsn(int a, int a) {
      if (a.mv != null) {
         a.mv.visitIincInsn(a, a);
      }

   }

   public void visitTableSwitchInsn(int a, int a, Label a, Label... a) {
      if (a.mv != null) {
         a.mv.visitTableSwitchInsn(a, a, a, a);
      }

   }

   public void visitLookupSwitchInsn(Label a, int[] a, Label[] a) {
      if (a.mv != null) {
         a.mv.visitLookupSwitchInsn(a, a, a);
      }

   }

   public void visitMultiANewArrayInsn(String a, int a) {
      if (a.mv != null) {
         a.mv.visitMultiANewArrayInsn(a, a);
      }

   }

   public AnnotationVisitor visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      if (a.api < 327680) {
         throw new RuntimeException();
      } else {
         return a.mv != null ? a.mv.visitInsnAnnotation(a, a, a, a) : null;
      }
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      if (a.mv != null) {
         a.mv.visitTryCatchBlock(a, a, a, a);
      }

   }

   public AnnotationVisitor visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      if (a.api < 327680) {
         throw new RuntimeException();
      } else {
         return a.mv != null ? a.mv.visitTryCatchAnnotation(a, a, a, a) : null;
      }
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      if (a.mv != null) {
         a.mv.visitLocalVariable(a, a, a, a, a, a);
      }

   }

   public AnnotationVisitor visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      if (a.api < 327680) {
         throw new RuntimeException();
      } else {
         return a.mv != null ? a.mv.visitLocalVariableAnnotation(a, a, a, a, a, a, a) : null;
      }
   }

   public void visitLineNumber(int a, Label a) {
      if (a.mv != null) {
         a.mv.visitLineNumber(a, a);
      }

   }

   public void visitMaxs(int a, int a) {
      if (a.mv != null) {
         a.mv.visitMaxs(a, a);
      }

   }

   public void visitEnd() {
      if (a.mv != null) {
         a.mv.visitEnd();
      }

   }
}
