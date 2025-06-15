package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class MultiANewArrayInsnNode extends AbstractInsnNode {
   public String desc;
   public int dims;

   public MultiANewArrayInsnNode(String a, int a) {
      super(197);
      a.desc = a;
      a.dims = a;
   }

   public int getType() {
      return 13;
   }

   public void accept(MethodVisitor a) {
      a.visitMultiANewArrayInsn(a.desc, a.dims);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new MultiANewArrayInsnNode(a.desc, a.dims)).cloneAnnotations(a);
   }
}
