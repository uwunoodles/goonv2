package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class FieldInsnNode extends AbstractInsnNode {
   public String owner;
   public String name;
   public String desc;

   public FieldInsnNode(int a, String a, String a, String a) {
      super(a);
      a.owner = a;
      a.name = a;
      a.desc = a;
   }

   public void setOpcode(int a) {
      a.opcode = a;
   }

   public int getType() {
      return 4;
   }

   public void accept(MethodVisitor a) {
      a.visitFieldInsn(a.opcode, a.owner, a.name, a.desc);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new FieldInsnNode(a.opcode, a.owner, a.name, a.desc)).cloneAnnotations(a);
   }
}
