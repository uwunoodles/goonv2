package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.MethodVisitor;

public class InvokeDynamicInsnNode extends AbstractInsnNode {
   public String name;
   public String desc;
   public Handle bsm;
   public Object[] bsmArgs;

   public InvokeDynamicInsnNode(String a, String a, Handle a, Object... a) {
      super(186);
      a.name = a;
      a.desc = a;
      a.bsm = a;
      a.bsmArgs = a;
   }

   public int getType() {
      return 6;
   }

   public void accept(MethodVisitor a) {
      a.visitInvokeDynamicInsn(a.name, a.desc, a.bsm, a.bsmArgs);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new InvokeDynamicInsnNode(a.name, a.desc, a.bsm, a.bsmArgs)).cloneAnnotations(a);
   }
}
