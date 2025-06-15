package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.TypePath;

public class TypeAnnotationNode extends AnnotationNode {
   public int typeRef;
   public TypePath typePath;

   public TypeAnnotationNode(int a, TypePath a, String a) {
      this(327680, a, a, a);
      if (a.getClass() != TypeAnnotationNode.class) {
         throw new IllegalStateException();
      }
   }

   public TypeAnnotationNode(int a, int a, TypePath a, String a) {
      super(a, a);
      a.typeRef = a;
      a.typePath = a;
   }
}
