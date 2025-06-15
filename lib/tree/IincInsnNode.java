package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class IincInsnNode extends AbstractInsnNode {
   public int var;
   public int incr;

   public IincInsnNode(int a, int a) {
      super(132);
      a.var = a;
      a.incr = a;
   }

   public int getType() {
      return 10;
   }

   public void accept(MethodVisitor a) {
      a.visitIincInsn(a.var, a.incr);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new IincInsnNode(a.var, a.incr)).cloneAnnotations(a);
   }
}
