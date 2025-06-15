package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.MethodVisitor;

public class LocalVariableNode {
   public String name;
   public String desc;
   public String signature;
   public LabelNode start;
   public LabelNode end;
   public int index;

   public LocalVariableNode(String a, String a, String a, LabelNode a, LabelNode a, int a) {
      a.name = a;
      a.desc = a;
      a.signature = a;
      a.start = a;
      a.end = a;
      a.index = a;
   }

   public void accept(MethodVisitor a) {
      a.visitLocalVariable(a.name, a.desc, a.signature, a.start.getLabel(), a.end.getLabel(), a.index);
   }
}
