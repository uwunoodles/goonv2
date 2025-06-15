package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class InsnNode extends AbstractInsnNode {
   public InsnNode(int a) {
      super(a);
   }

   public int getType() {
      return 0;
   }

   public void accept(MethodVisitor a) {
      a.visitInsn(a.opcode);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new InsnNode(a.opcode)).cloneAnnotations(a);
   }
}
