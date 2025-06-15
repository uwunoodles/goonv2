package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.MethodVisitor;

public class ParameterNode {
   public String name;
   public int access;

   public ParameterNode(String a, int a) {
      a.name = a;
      a.access = a;
   }

   public void accept(MethodVisitor a) {
      a.visitParameter(a.name, a.access);
   }
}
