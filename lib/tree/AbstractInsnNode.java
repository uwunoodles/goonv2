package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public abstract class AbstractInsnNode {
   public static final int INSN = 0;
   public static final int INT_INSN = 1;
   public static final int VAR_INSN = 2;
   public static final int TYPE_INSN = 3;
   public static final int FIELD_INSN = 4;
   public static final int METHOD_INSN = 5;
   public static final int INVOKE_DYNAMIC_INSN = 6;
   public static final int JUMP_INSN = 7;
   public static final int LABEL = 8;
   public static final int LDC_INSN = 9;
   public static final int IINC_INSN = 10;
   public static final int TABLESWITCH_INSN = 11;
   public static final int LOOKUPSWITCH_INSN = 12;
   public static final int MULTIANEWARRAY_INSN = 13;
   public static final int FRAME = 14;
   public static final int LINE = 15;
   protected int opcode;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   AbstractInsnNode prev;
   AbstractInsnNode next;
   int index;

   protected AbstractInsnNode(int a) {
      a.opcode = a;
      a.index = -1;
   }

   public int getOpcode() {
      return a.opcode;
   }

   public abstract int getType();

   public AbstractInsnNode getPrevious() {
      return a.prev;
   }

   public AbstractInsnNode getNext() {
      return a.next;
   }

   public abstract void accept(MethodVisitor var1);

   protected final void acceptAnnotations(MethodVisitor a) {
      int a = a.visibleTypeAnnotations == null ? 0 : a.visibleTypeAnnotations.size();

      int a;
      TypeAnnotationNode a;
      for(a = 0; a < a; ++a) {
         a = (TypeAnnotationNode)a.visibleTypeAnnotations.get(a);
         a.accept(a.visitInsnAnnotation(a.typeRef, a.typePath, a.desc, true));
      }

      a = a.invisibleTypeAnnotations == null ? 0 : a.invisibleTypeAnnotations.size();

      for(a = 0; a < a; ++a) {
         a = (TypeAnnotationNode)a.invisibleTypeAnnotations.get(a);
         a.accept(a.visitInsnAnnotation(a.typeRef, a.typePath, a.desc, false));
      }

   }

   public abstract AbstractInsnNode clone(Map<LabelNode, LabelNode> var1);

   static LabelNode clone(LabelNode a, Map<LabelNode, LabelNode> a) {
      return (LabelNode)a.get(a);
   }

   static LabelNode[] clone(List<LabelNode> a, Map<LabelNode, LabelNode> a) {
      LabelNode[] a = new LabelNode[a.size()];

      for(int a = 0; a < a.length; ++a) {
         a[a] = (LabelNode)a.get(a.get(a));
      }

      return a;
   }

   protected final AbstractInsnNode cloneAnnotations(AbstractInsnNode a) {
      int a;
      TypeAnnotationNode a;
      TypeAnnotationNode a;
      if (a.visibleTypeAnnotations != null) {
         a.visibleTypeAnnotations = new ArrayList();

         for(a = 0; a < a.visibleTypeAnnotations.size(); ++a) {
            a = (TypeAnnotationNode)a.visibleTypeAnnotations.get(a);
            a = new TypeAnnotationNode(a.typeRef, a.typePath, a.desc);
            a.accept(a);
            a.visibleTypeAnnotations.add(a);
         }
      }

      if (a.invisibleTypeAnnotations != null) {
         a.invisibleTypeAnnotations = new ArrayList();

         for(a = 0; a < a.invisibleTypeAnnotations.size(); ++a) {
            a = (TypeAnnotationNode)a.invisibleTypeAnnotations.get(a);
            a = new TypeAnnotationNode(a.typeRef, a.typePath, a.desc);
            a.accept(a);
            a.invisibleTypeAnnotations.add(a);
         }
      }

      return a;
   }
}
