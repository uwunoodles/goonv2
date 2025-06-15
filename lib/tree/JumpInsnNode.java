package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class JumpInsnNode extends AbstractInsnNode {
   public LabelNode label;

   public JumpInsnNode(int a, LabelNode a) {
      super(a);
      a.label = a;
   }

   public void setOpcode(int a) {
      a.opcode = a;
   }

   public int getType() {
      return 7;
   }

   public void accept(MethodVisitor a) {
      a.visitJumpInsn(a.opcode, a.label.getLabel());
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a) {
      return (new JumpInsnNode(a.opcode, clone(a.label, a))).cloneAnnotations(a);
   }
}
