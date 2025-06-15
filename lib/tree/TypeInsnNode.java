package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class TypeInsnNode extends AbstractInsnNode {
   public String desc;

   public TypeInsnNode(int a, String a) {
      super(a);
      a.desc = a;
   }

   public void setOpcode(int a) {
      a.opcode = a;
   }

   public int getType() {
      return 3;
   }

   public void accept(MethodVisitor a) {
      a.visitTypeInsn(a.opcode, a.desc);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a1) {
      return (new TypeInsnNode(a.opcode, a.desc)).cloneAnnotations(a);
   }
}
