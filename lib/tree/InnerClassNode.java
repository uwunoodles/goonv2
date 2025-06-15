package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.ClassVisitor;

public class InnerClassNode {
   public String name;
   public String outerName;
   public String innerName;
   public int access;

   public InnerClassNode(String a, String a, String a, int a) {
      a.name = a;
      a.outerName = a;
      a.innerName = a;
      a.access = a;
   }

   public void accept(ClassVisitor a) {
      a.visitInnerClass(a.name, a.outerName, a.innerName, a.access);
   }
}
