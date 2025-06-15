package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class VarInsnNode extends AbstractInsnNode {
   public int var;

   public VarInsnNode(int a, int a) {
      super(a);
      a.var = a;
   }

   public void setOpcode(int a) {
      a.opcode = a;
   }

   public int getType() {
      return 2;
   }

   public void accept(MethodVisitor a) {
      a.visitVarInsn(a.opcode, a.var);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new VarInsnNode(a.opcode, a.var)).cloneAnnotations(a);
   }
}
