package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class IntInsnNode extends AbstractInsnNode {
   public int operand;

   public IntInsnNode(int a, int a) {
      super(a);
      a.operand = a;
   }

   public void setOpcode(int a) {
      a.opcode = a;
   }

   public int getType() {
      return 1;
   }

   public void accept(MethodVisitor a) {
      a.visitIntInsn(a.opcode, a.operand);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new IntInsnNode(a.opcode, a.operand)).cloneAnnotations(a);
   }
}
