package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class LdcInsnNode extends AbstractInsnNode {
   public Object cst;

   public LdcInsnNode(Object a) {
      super(18);
      a.cst = a;
   }

   public int getType() {
      return 9;
   }

   public void accept(MethodVisitor a) {
      a.visitLdcInsn(a.cst);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new LdcInsnNode(a.cst)).cloneAnnotations(a);
   }
}
