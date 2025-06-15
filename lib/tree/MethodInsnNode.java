package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class MethodInsnNode extends AbstractInsnNode {
   public String owner;
   public String name;
   public String desc;
   public boolean itf;

   /** @deprecated */
   @Deprecated
   public MethodInsnNode(int a, String a, String a, String a) {
      this(a, a, a, a, a == 185);
   }

   public MethodInsnNode(int a, String a, String a, String a, boolean a) {
      super(a);
      a.owner = a;
      a.name = a;
      a.desc = a;
      a.itf = a;
   }

   public void setOpcode(int a) {
      a.opcode = a;
   }

   public int getType() {
      return 5;
   }

   public void accept(MethodVisitor a) {
      a.visitMethodInsn(a.opcode, a.owner, a.name, a.desc, a.itf);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return new MethodInsnNode(a.opcode, a.owner, a.name, a.desc, a.itf);
   }
}
